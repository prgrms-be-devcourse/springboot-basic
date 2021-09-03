CREATE TABLE vouchers (
    voucher_id  BINARY(16) PRIMARY KEY,
    type        VARCHAR(20) NOT NULL,
    amount      INT         NOT NULL,
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    customer_id BINARY(16)
);