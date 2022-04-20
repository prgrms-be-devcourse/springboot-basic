create database voucher_mgmt;

use voucher_mgmt;

CREATE TABLE customers
(
    customer_id     BINARY(16)  PRIMARY KEY,
    name            varchar(20) NOT NULL,
    email           varchar(50) NOT NULL,
    last_login_at   datetime(6) DEFAULT NULL,
    created_at      datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT      unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id     BINARY(16)  PRIMARY KEY,
    type            varchar(20) NOT NULL,
    value           bigint(50) NOT NULL,
    CONSTRAINT      unq_voucher_type   UNIQUE (type, value)
);


CREATE TABLE wallet
(
    voucher_id     BINARY(16),
    customer_id    BINARY(16),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id),
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
    CONSTRAINT     unq_wallet   UNIQUE (voucher_id, customer_id)
);
