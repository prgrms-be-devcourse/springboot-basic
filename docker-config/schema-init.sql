CREATE DATABASE IF NOT EXISTS order_mgmt;
USE order_mgmt;
CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id        BINARY(16) PRIMARY KEY,
    discount_value    varchar(20) NOT NULL,
    voucher_type      varchar(30) NOT NULL,
    owned_customer_id BINARY(16)           DEFAULT NULL,
    created_at        datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
CREATE TABLE IF NOT EXISTS customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6)          DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    is_blacklist  BOOLEAN              DEFAULT FALSE,
    CONSTRAINT unq_user_email UNIQUE (email)
);