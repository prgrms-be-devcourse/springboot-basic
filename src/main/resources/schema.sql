CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at DATETIME(6)          DEFAULT NULL,
    created_at    DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unique_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id   BINARY(16) PRIMARY KEY,
    amount       BIGINT      DEFAULT 0,
    voucher_kind VARCHAR(20) NOT NULL,
    created_at   DATETIME(6) default CURRENT_TIMESTAMP(6)
);