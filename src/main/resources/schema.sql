CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(20) NOT NULL,
    last_login_at datetime(6) DEFAULT NULL,
    created_at    datetime(6) default current_timestamp(6),
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id   BINARY(16) PRIMARY KEY,
    amount       BIGINT      DEFAULT 0,
    voucher_kind VARCHAR(50) NOT NULL,
    created_at   DATETIME(6) default CURRENT_TIMESTAMP(6)
);

CREATE TABLE wallet
(
    wallet_id   BINARY(16) PRIMARY KEY,
    customer_id BINARY(16),
    voucher_id  BINARY(16),
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id) ON DELETE CASCADE
);