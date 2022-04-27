create database order_mgmt;

use order_mgmt;

drop table if exists customers;
drop table if exists vouchers;

CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        VARCHAR(20) NOT NULL,
    email       VARCHAR(50) NOT NULL,
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email),
    CONSTRAINT unq_user_name UNIQUE (name)
);

CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    type       VARCHAR(10) not null,
    amount     INT         not null
);

CREATE TABLE products
(
    product_id BINARY(16) PRIMARY KEY,
    name       VARCHAR(20) NOT NULL,
    price      INT         NOT NULL,
    stock      INT         NOT NULL,
    status     VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL
);