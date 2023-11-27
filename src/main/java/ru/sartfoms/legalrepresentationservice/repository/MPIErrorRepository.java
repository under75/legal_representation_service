package ru.sartfoms.legalrepresentationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sartfoms.legalrepresentationservice.entity.CompositeKey;
import ru.sartfoms.legalrepresentationservice.entity.MPIError;

public interface MPIErrorRepository extends JpaRepository<MPIError, CompositeKey> {

}
