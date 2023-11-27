package ru.sartfoms.legalrepresentationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.legalrepresentationservice.entity.MPIReq;
import ru.sartfoms.legalrepresentationservice.entity.MPIReqId;

public interface MPIReqRepository extends JpaRepository<MPIReq, MPIReqId> {
	
	MPIReq getByExtrid(String extRid);
}
