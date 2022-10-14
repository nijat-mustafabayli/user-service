CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

create table users
(
    id            uuid primary key DEFAULT uuid_generate_v4(),
    name          VARCHAR(255),
    email         VARCHAR(255),
    age           INTEGER,
    phone_number  VARCHAR(255),
    created_date  timestamp with time zone NOT NULL,
    modified_date timestamp with time zone NOT NULL
);