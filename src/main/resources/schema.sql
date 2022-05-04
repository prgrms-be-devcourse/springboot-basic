CREATE TABLE vouchers
(
    voucher_id     BINARY(16) PRIMARY KEY,
    discount_value bigint NOT NULL,
    created_at    datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    voucher_type   enum('FIXED', 'PERCENT') NOT NULL
);

CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    created_at    datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE wallets
(
    wallet_id   BINARY(16)  PRIMARY KEY,
    customer_id BINARY(16)  NOT NULL,
    voucher_id BINARY(16)   NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES  customers (customer_id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id)
);