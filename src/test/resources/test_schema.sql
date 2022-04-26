create database test_order_mgmt;

use test_order_mgmt;

drop table if exists customers;
drop table if exists vouchers;

CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL,
    created_at  datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email),
    CONSTRAINT unq_user_name UNIQUE (name)
);

CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    type       varchar(10) not null,
    amount     int         not null
);

