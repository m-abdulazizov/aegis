create table organizations (
                               id uuid primary key,
                               name varchar(255) not null,
                               registration_number varchar(100) not null,
                               license_number varchar(100),
                               type varchar(50) not null,
                               status varchar(50) not null,
                               wallet_address varchar(255),
                               created_at timestamp not null,
                               updated_at timestamp
);

create unique index uq_organizations_registration_number
    on organizations(lower(registration_number));

create unique index uq_organizations_license_number
    on organizations(lower(license_number))
    where license_number is not null;

create index idx_organizations_type on organizations(type);
create index idx_organizations_status on organizations(status);