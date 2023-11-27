package ru.sartfoms.legalrepresentationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.legalrepresentationservice.entity.CompositeKey;
import ru.sartfoms.legalrepresentationservice.entity.LegalRepresentRes;

public interface LegalRepresentResRepository extends JpaRepository<LegalRepresentRes, CompositeKey> {

}
