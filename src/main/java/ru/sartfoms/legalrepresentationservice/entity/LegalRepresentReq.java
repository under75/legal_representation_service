package ru.sartfoms.legalrepresentationservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MPI_LEGAL_REPRESENT_REQ", schema="OMCOWNER")
public class LegalRepresentReq {
	@Id
	@Column(name = "rid")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
	@SequenceGenerator(name = "id_sequence", sequenceName = "OMCOWNER.MPI_SEQ", allocationSize = 1)
	private Long rid;
	
	@Column(name = "ridorig")
	private Long ridorig;

	@Column(name = "nr")
	private Integer nr;
	
	@Column(name = "oip")
	private String oip;

	public Long getRid() {
		return rid;
	}

	public Long getRidorig() {
		return ridorig;
	}

	public Integer getNr() {
		return nr;
	}

	public String getOip() {
		return oip;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public void setRidorig(Long ridorig) {
		this.ridorig = ridorig;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public void setOip(String oip) {
		this.oip = oip;
	}
	
	
}
