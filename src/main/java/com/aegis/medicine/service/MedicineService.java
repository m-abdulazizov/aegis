package com.aegis.medicine.service;

import com.aegis.common.exception.BusinessException;
import com.aegis.medicine.entity.Medicine;
import com.aegis.medicine.entity.MedicineStatus;
import com.aegis.medicine.payload.MedicineCreateRequest;
import com.aegis.medicine.payload.MedicineResponse;
import com.aegis.medicine.repository.MedicineRepository;
import com.aegis.organization.entity.Organization;
import com.aegis.organization.entity.OrganizationType;
import com.aegis.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final OrganizationService organizationService;

    @Transactional
    public MedicineResponse create(MedicineCreateRequest request) {
        validateUniqueRegistrationNumber(request.registrationNumber());

        Organization manufacturer = organizationService.getActiveEntityById(request.manufacturerId());
        validateManufacturer(manufacturer);

        Medicine medicine = Medicine.builder()
                .name(request.name().trim())
                .genericName(trimToNull(request.genericName()))
                .dosage(request.dosage().trim())
                .dosageForm(request.dosageForm().trim())
                .registrationNumber(request.registrationNumber().trim())
                .manufacturer(manufacturer)
                .status(MedicineStatus.ACTIVE)
                .build();

        Medicine saved = medicineRepository.save(medicine);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public MedicineResponse getById(UUID id) {
        Medicine medicine = getActiveEntityById(id);
        return toResponse(medicine);
    }

    @Transactional(readOnly = true)
    public Page<MedicineResponse> getAll(Pageable pageable) {
        return medicineRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Medicine getActiveEntityById(UUID id) {
        return medicineRepository.findByIdAndStatusNot(id, MedicineStatus.WITHDRAWN)
                .orElseThrow(() -> BusinessException.notFound("Medicine not found"));
    }

    private void validateUniqueRegistrationNumber(String registrationNumber) {
        if (medicineRepository.existsByRegistrationNumberIgnoreCase(registrationNumber.trim())) {
            throw BusinessException.conflict("Medicine with this registration number already exists");
        }
    }

    private void validateManufacturer(Organization organization) {
        if (organization.getType() != OrganizationType.MANUFACTURER) {
            throw BusinessException.badRequest("Only manufacturer organizations can register medicines");
        }
    }

    private MedicineResponse toResponse(Medicine medicine) {
        return new MedicineResponse(
                medicine.getId(),
                medicine.getName(),
                medicine.getGenericName(),
                medicine.getDosage(),
                medicine.getDosageForm(),
                medicine.getRegistrationNumber(),
                medicine.getManufacturer().getId(),
                medicine.getManufacturer().getName(),
                medicine.getStatus(),
                medicine.getCreatedAt(),
                medicine.getUpdatedAt()
        );
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }
}