package com.aegis.medicine.repository;

import com.aegis.medicine.entity.Medicine;
import com.aegis.medicine.entity.MedicineStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MedicineRepository extends JpaRepository<Medicine, UUID>
{
    Optional<Medicine> findByIdAndStatusNot(UUID id, MedicineStatus status);

    boolean existsByRegistrationNumberIgnoreCase(String registrationNumber);
}
