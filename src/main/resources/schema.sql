CREATE Database order_mgmt_w3;

USE order_mgmt_w3;

CREATE TABLE customers
(
    customer_id    BINARY(16) PRIMARY KEY,
    name           varchar(20) NOT NULL,
    email          varchar(50) NOT NULL,
    last_login_at  datetime(6) DEFAULT NULL,
    created_at     datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id      BINARY(16) PRIMARY KEY,
    voucher_type    varchar(50) NOT NULL,
    amount          Integer     NOT NULL,
    CONSTRAINT unq_voucher_id UNIQUE (voucher_id)
);
