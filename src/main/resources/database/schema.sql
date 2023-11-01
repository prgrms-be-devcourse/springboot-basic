CREATE DATABASE order_management;
USE order_management;
CREATE TABLE customer
(
    id         BINARY(16) PRIMARY KEY,
    name       varchar(20) NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    is_banned  boolean     NOT NULL DEFAULT FALSE
);

CREATE TABLE voucher
(
    id              BINARY(16) PRIMARY KEY,
    name            varchar(20) NOT NULL,
    discount_amount float       NOT NULL DEFAULT FALSE,
    created_at      datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    voucher_type    varchar(20) NOT NULL
);

CREATE TABLE wallet
(
    id          BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) NOT NULL,
    voucher_id  BINARY(16) NOT NULL,
    CONSTRAINT fk_pk_customer FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
    CONSTRAINT fk_pk_voucher FOREIGN KEY (voucher_id) REFERENCES voucher (id) ON DELETE CASCADE
);
