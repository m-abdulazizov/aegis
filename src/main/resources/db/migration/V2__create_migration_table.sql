create table medicines
(
    id                  uuid primary key,
    name                varchar(255) not null,
    generic_name        varchar(255),
    dosage              varchar(100) not null,
    dosage_form         varchar(100) not null,
    registration_number varchar(100) not null,
    manufacturer_id     uuid         not null,
    status              varchar(50)  not null,
    created_at          timestamp    not null,
    updated_at          timestamp,

    constraint fk_medicines_manufacturer
        foreign key (manufacturer_id)
            references organizations (id)
);

create unique index uq_medicines_registration_number
on medicines(lower(registration_number));

create index idx_medicines_manufacturer_id on medicines(manufacturer_id);
create index idx_medicines_status on medicines(status);
create index idx_medicines_name on medicines(lower(name));