package ru.sartfoms.legalrepresentationservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.legalrepresentationservice.entity.LegalRepresent;

public interface LegalRepresentRepository extends JpaRepository<LegalRepresent, Long> {

	Collection<LegalRepresent> findByDtReqIsNull();

}
