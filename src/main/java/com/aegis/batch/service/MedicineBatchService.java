package com.aegis.batch.service;

import com.aegis.batch.entity.MedicineBatch;
import com.aegis.batch.entity.MedicineBatchStatus;
import com.aegis.batch.payload.MedicineBatchCreateRequest;
import com.aegis.batch.payload.MedicineBatchResponse;
import com.aegis.batch.repository.MedicineBatchRepository;
import com.aegis.common.exception.BusinessException;
import com.aegis.medicine.entity.Medicine;
import com.aegis.medicine.service.MedicineService;
import com.aegis.organization.entity.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicineBatchService {

    private final MedicineBatchRepository medicineBatchRepository;
    private final MedicineService medicineService;

    @Transactional
    public MedicineBatchResponse create(MedicineBatchCreateRequest request) {
        validateUniqueBatchNumber(request.batchNumber());
        validateDates(request.manufactureDate(), request.expiryDate());

        Medicine medicine = medicineService.getActiveEntityById(request.medicineId());
        Organization manufacturer = medicine.getManufacturer();

        MedicineBatch batch = MedicineBatch.builder()
                .batchNumber(request.batchNumber().trim())
                .medicine(medicine)
                .quantity(request.quantity())
                .manufactureDate(request.manufactureDate())
                .expiryDate(request.expiryDate())
                .status(MedicineBatchStatus.CREATED)
                .currentOwner(manufacturer)
                .build();

        MedicineBatch saved = medicineBatchRepository.save(batch);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public MedicineBatchResponse getById(UUID id) {
        MedicineBatch batch = getActiveEntityById(id);
        return toResponse(batch);
    }

    @Transactional(readOnly = true)
    public Page<MedicineBatchResponse> getAll(Pageable pageable) {
        return medicineBatchRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public MedicineBatch getActiveEntityById(UUID id) {
        return medicineBatchRepository.findByIdAndStatusNot(id, MedicineBatchStatus.BLOCKED)
                .orElseThrow(() -> BusinessException.notFound("Medicine batch not found"));
    }

    private void validateUniqueBatchNumber(String batchNumber) {
        if (medicineBatchRepository.existsByBatchNumberIgnoreCase(batchNumber.trim())) {
            throw BusinessException.conflict("Medicine batch with this batch number already exists");
        }
    }

    private void validateDates(LocalDate manufactureDate, LocalDate expiryDate) {
        if (!expiryDate.isAfter(manufactureDate)) {
            throw BusinessException.badRequest("Expiry date must be after manufacture date");
        }
    }

    private MedicineBatchResponse toResponse(MedicineBatch batch) {
        return new MedicineBatchResponse(
                batch.getId(),
                batch.getBatchNumber(),
                batch.getMedicine().getId(),
                batch.getMedicine().getName(),
                batch.getMedicine().getDosage(),
                batch.getMedicine().getDosageForm(),
                batch.getQuantity(),
                batch.getManufactureDate(),
                batch.getExpiryDate(),
                batch.getStatus(),
                batch.getCurrentOwner().getId(),
                batch.getCurrentOwner().getName(),
                batch.getCreatedAt(),
                batch.getUpdatedAt()
        );
    }
}