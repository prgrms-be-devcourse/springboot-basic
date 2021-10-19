CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL,
    created_at  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id   BINARY(16) PRIMARY KEY,
    voucher_type enum ('FIXED', 'PERCENT') NOT NULL,
    discount     bigint   NOT NULL,
    created_at   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wallets
(
    wallet_id   BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) NOT NULL,
    voucher_id  BINARY(16) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id)
);