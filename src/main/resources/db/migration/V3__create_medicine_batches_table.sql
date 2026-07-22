create table medicine_batches (
                                  id uuid primary key,
                                  batch_number varchar(100) not null,
                                  medicine_id uuid not null,
                                  quantity bigint not null,
                                  manufacture_date date not null,
                                  expiry_date date not null,
                                  status varchar(50) not null,
                                  current_owner_id uuid not null,
                                  created_at timestamp not null,
                                  updated_at timestamp,

                                  constraint fk_medicine_batches_medicine
                                      foreign key (medicine_id)
                                          references medicines(id),

                                  constraint fk_medicine_batches_current_owner
                                      foreign key (current_owner_id)
                                          references organizations(id),

                                  constraint chk_medicine_batches_quantity_positive
                                      check (quantity > 0),

                                  constraint chk_medicine_batches_expiry_after_manufacture
                                      check (expiry_date > manufacture_date)
);

create unique index uq_medicine_batches_batch_number
    on medicine_batches(lower(batch_number));

create index idx_medicine_batches_medicine_id on medicine_batches(medicine_id);
create index idx_medicine_batches_current_owner_id on medicine_batches(current_owner_id);
create index idx_medicine_batches_status on medicine_batches(status);