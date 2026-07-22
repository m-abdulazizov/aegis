package com.aegis.batch.repository;

import com.aegis.batch.entity.MedicineBatch;
import com.aegis.batch.entity.MedicineBatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MedicineBatchRepository extends JpaRepository<MedicineBatch, UUID> {

    Optional<MedicineBatch> findByIdAndStatusNot(UUID id, MedicineBatchStatus status);

    boolean existsByBatchNumberIgnoreCase(String batchNumber);
}