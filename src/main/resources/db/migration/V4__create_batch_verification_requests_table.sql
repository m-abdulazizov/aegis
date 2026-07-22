create table batch_verification_requests(
    id uuid primary key,
    batch_id uuid not null,
    status varchar(50) not null,
    reviewed_by_id uuid,
    submit_notes text,
    review_notes text,
    submitted_at timestamp not null,
    reviewed_at timestamp,
    created_at timestamp not null,
    updated_at timestamp,

    constraint fk_batch_verification_requests_batch
                                        foreign key (batch_id)
                                        references medicine_batches(id),

    constraint fk_batch_verification_requests_submitted_by
                                        foreign key (submitted_by_id)
                                        references organizations(id),

    constraint fk_batch_verification_requests_reviewed_by
                                        foreign key (reviewed_by_id)
                                        references organizations(id)

);

create unique index
uq_batch_verification_requests_pending_batch
    on batch_verification_requests(batch_id)
    where status = 'PENDING';

create index idx_batch_verification_requests_batch_id
    on batch_verification_requests(batch_id);

create index idx_batch_verification_requests_status
    on batch_verification_requests(status);

create index idx_batch_verification_requests_submitted_by_id
    on batch_verification_requests(submitted_by_id);

create index idx_batch_verification_requests_reviewed_by_id
     on batch_verification_requests(reviewed_by_id);

