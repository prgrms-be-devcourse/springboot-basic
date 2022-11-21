DROP TABLE IF EXISTS vouchers;
CREATE TABLE vouchers
(
    voucher_id        BINARY(16) PRIMARY KEY,
    discount_value    varchar(20) NOT NULL,
    voucher_type      varchar(30) NOT NULL,
    owned_customer_id BINARY(16)           DEFAULT NULL,
    created_at        datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);

DROP TABLE IF EXISTS customers;
CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6)          DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    is_blacklist  BOOLEAN              DEFAULT FALSE,
    CONSTRAINT unq_user_email UNIQUE (email)
);