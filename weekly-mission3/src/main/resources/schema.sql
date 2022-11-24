CREATE TABLE vouchers
(
    voucher_id  BINARY(16) PRIMARY KEY,
    type        VARCHAR(50) NOT NULL,
    discount    INT NOT NULL,
    created_at  datetime DEFAULT CURRENT_TIMESTAMP
);