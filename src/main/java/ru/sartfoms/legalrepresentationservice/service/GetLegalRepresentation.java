package ru.sartfoms.legalrepresentationservice.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.support.MarshallingUtils;

import ru.sartfoms.legalrepresentationservice.entity.LegalRepresent;
import ru.sartfoms.legalrepresentationservice.entity.LegalRepresentReq;
import ru.sartfoms.legalrepresentationservice.entity.LegalRepresentRes;
import ru.sartfoms.legalrepresentationservice.entity.MPIError;
import ru.sartfoms.legalrepresentationservice.entity.MPIReq;
import ru.sartfoms.legalrepresentationservice.model.Ridoip;
import ru.sartfoms.legalrepresentationservice.repository.LegalRepresentRepository;
import ru.sartfoms.legalrepresentationservice.repository.LegalRepresentReqRepository;
import ru.sartfoms.legalrepresentationservice.repository.LegalRepresentResRepository;
import ru.sartfoms.legalrepresentationservice.repository.MPIErrorRepository;
import ru.sartfoms.legalrepresentationservice.repository.MPIReqRepository;
import ru.sartfoms.schemas.generated.GetLegalRepresentationRequest;
import ru.sartfoms.schemas.generated.GetLegalRepresentationResponse;
import ru.sartfoms.schemas.generated.LegalRepresentation;
import ru.sartfoms.schemas.generated.ResponseErrorData;

@Service
public class GetLegalRepresentation extends LegalRepresentationService {
	private final WebServiceTemplate template;
	private final MPIReqRepository mpiReqRepository;
	private final MPIErrorRepository mpiErrorRepository;
	private final LegalRepresentRepository legalRepresentRepository;
	private final LegalRepresentResRepository legalRepresentResRepository;
	private final LegalRepresentReqRepository legalRepresentReqRepository;
	private final RSocketRequester.Builder rsBuilder;
	@Value("${rsocketrequester.port}")
	private int rsrPort;

	public GetLegalRepresentation(LegalRepresentRepository legalRepresentRepository,
			MPIErrorRepository mpiErrorRepository, WebServiceTemplate template, MPIReqRepository mpiReqRepository,
			LegalRepresentResRepository legalRepresentResRepository, RSocketRequester.Builder rsBuilder, LegalRepresentReqRepository legalRepresentReqRepository) {
		this.template = template;
		this.mpiReqRepository = mpiReqRepository;
		this.mpiErrorRepository = mpiErrorRepository;
		this.legalRepresentRepository = legalRepresentRepository;
		this.legalRepresentResRepository = legalRepresentResRepository;
		this.legalRepresentReqRepository = legalRepresentReqRepository;
		this.rsBuilder = rsBuilder;
	}

	@Override
	@Scheduled(cron = "0 0/1 8-23 * * *")
	protected void process() {
		Collection<LegalRepresent> entities = legalRepresentRepository.findByDtReqIsNull();
		entities.forEach(entity -> {
			String extRid = UUID.randomUUID().toString();
			try {
				GetLegalRepresentationResponse response = template.sendAndReceive(new WebServiceMessageCallback() {

					@Override
					public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
						MarshallingUtils.marshal(template.getMarshaller(), getRequest(), message);

						ByteArrayOutputStream out = new ByteArrayOutputStream();
						message.writeTo(out);
						MPIReq mpiReq = new MPIReq();
						mpiReq.setRid(entity.getRid());
						mpiReq.setDt(LocalDateTime.now());
						mpiReq.setReq(out.toByteArray());
						mpiReq.setExtrid(extRid);

						mpiReqRepository.save(mpiReq);
					}

					private GetLegalRepresentationRequest getRequest() {
						GetLegalRepresentationRequest request = new GetLegalRepresentationRequest();
						request.setExternalRequestId(extRid);
						request.setIsActual(entity.getIsActual());
						request.setOip(entity.getOip());
						if (entity.getType() != null) {
							String[] types = entity.getType().split(" ");
							for (String type : types) {
								request.getLegalRepType().add(type);
							}
						}

						return request;
					}

				}, new WebServiceMessageExtractor<GetLegalRepresentationResponse>() {

					@Override
					public GetLegalRepresentationResponse extractData(WebServiceMessage message)
							throws IOException, TransformerException {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						message.writeTo(out);
						MPIReq mpiReq = mpiReqRepository.getByExtrid(extRid);
						mpiReq.setResp(out.toByteArray());

						mpiReqRepository.save(mpiReq);

						return (GetLegalRepresentationResponse) MarshallingUtils.unmarshal(template.getUnmarshaller(),
								message);
					}

				});

				save(entity, response, extRid);

			} catch (SoapFaultClientException e) {
				entity.setDtReq(LocalDateTime.now());
				entity.setHasError(true);

				legalRepresentRepository.save(entity);

				MPIError errEntity = new MPIError();
				errEntity.setRid(entity.getRid());
				errEntity.setNr(1);
				errEntity.setCode(e.getClass().getSimpleName());
				errEntity.setMessage(e.getFaultStringOrReason());
				errEntity.setDtIns(LocalDateTime.now());
				errEntity.setExtrid(extRid);
				errEntity.setType(SOAP_ERR);

				mpiErrorRepository.save(errEntity);
			}

		});
	}

	private void save(LegalRepresent entity, GetLegalRepresentationResponse response, String extRid) {
		if (response.getErrors() != null && response.getErrors().getErrorItem().size() == 1 && response.getErrors()
				.getErrorItem().stream().findAny().get().getCode().trim().equals(INTERNAL_SERVICE_ERROR)) {
			// do nothing
		} else {
			entity.setDtReq(LocalDateTime.now());
			entity.setHasError(response.getErrors() != null ? true : false);

			legalRepresentRepository.save(entity);
		}

		if (response.getErrors() != null) {
			Collection<ResponseErrorData> errors = response.getErrors().getErrorItem();
			int nr = 0;
			for (ResponseErrorData err : errors) {
				if (err.getCode().trim().equals(INTERNAL_SERVICE_ERROR))
					continue; // do nothing
				MPIError errEntity = new MPIError();
				errEntity.setRid(entity.getRid());
				errEntity.setNr(++nr);
				errEntity.setCode(err.getCode());
				errEntity.setMessage(err.getMessage());
				errEntity.setTag(err.getTag());
				errEntity.setValue(err.getValue());
				errEntity.setDtIns(LocalDateTime.now());
				errEntity.setExtrid(extRid);
				errEntity.setType(LOGIC_ERR);

				mpiErrorRepository.save(errEntity);
			}
		} else {
			if (response.getLegalRepresentation() != null) {
				List<LegalRepresentation> legalRepresentations = response.getLegalRepresentation()
						.getLegalRepresentationItem();
				for (LegalRepresentation legalRepresentation : legalRepresentations) {
					LegalRepresentRes result = new LegalRepresentRes();
					result.setRid(entity.getRid());
					result.setNr(legalRepresentations.indexOf(legalRepresentation));
					result.setOip(legalRepresentation.getOip());
					if (legalRepresentation.getLegRepDateB() != null)
						result.setEffDate(legalRepresentation.getLegRepDateB().toGregorianCalendar().toZonedDateTime()
								.toLocalDate());
					if (legalRepresentation.getLegRepDateE() != null)
						result.setExpDate(legalRepresentation.getLegRepDateE().toGregorianCalendar().toZonedDateTime()
								.toLocalDate());
					result.setStatus(legalRepresentation.getStatus());
					result.setType(legalRepresentation.getLegRepType());

					legalRepresentResRepository.save(result);
					
					LegalRepresentReq req = new LegalRepresentReq();
					req.setRidorig(result.getRid());
					req.setNr(result.getNr());
					req.setOip(result.getOip());
					
					req = legalRepresentReqRepository.save(req);

					RSocketRequester tcp = rsBuilder.tcp("localhost", rsrPort);
					tcp.route("persdata")
							.data(new Ridoip(req.getRid(), req.getOip())).send()
							.subscribe();
				}
				entity.setCount(legalRepresentations.size());
			} else {
				entity.setCount(0);
			}

			legalRepresentRepository.save(entity);
		}

	}
}
