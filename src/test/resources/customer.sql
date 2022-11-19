drop table if exists customer;

create table customer
(
    customer_id BINARY(16) primary key,
    name        varchar(50) not null,
    created_at  datetime(6) not null default CURRENT_TIMESTAMP(6)
);