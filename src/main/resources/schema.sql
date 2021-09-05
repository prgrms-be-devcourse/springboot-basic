CREATE TABLE customers
(
    customer_id    BINARY(16) PRIMARY KEY,
    name           varchar(20) NOT NULL,
    email          varchar(50) NOT NULL,
    last_login_at  datetime(6)             DEFAULT NULL,
    created_at     datetime(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id     BINARY(16) PRIMARY KEY,
    name           varchar(50) NOT NULL,
    voucher_type   ENUM('FIX', 'PERCENT') NOT NULL,
    discount       int(4) NOT NULL,
    created_at     datetime(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE wallets
(
    wallet_id BINARY(16) PRIMARY KEY,
    customer_id       BINARY(16),
    voucher_id        BINARY(16),
    created_at        datetime(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id)
);