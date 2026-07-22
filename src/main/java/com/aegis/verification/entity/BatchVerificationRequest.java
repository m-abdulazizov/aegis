package com.aegis.verification.entity;

import com.aegis.batch.entity.MedicineBatch;
import com.aegis.organization.entity.Organization;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batch_verification_requests")
public class BatchVerificationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "batch_id", nullable = false)
    private MedicineBatch batch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BatchVerificationStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by_id", nullable = false)
    private Organization submittedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by_id")
    private Organization reviewedBy;

    @Column(name = "submit_notes", columnDefinition = "text")
    private String submitNotes;

    @Column(name = "review_notes", columnDefinition = "text")
    private String reviewNotes;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (this.submittedAt == null) {
            this.submittedAt = LocalDateTime.now();
        }

        if (this.status == null) {
            this.status = BatchVerificationStatus.PENDING;
        }
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}