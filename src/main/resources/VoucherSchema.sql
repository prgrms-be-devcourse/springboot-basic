CREATE TABLE vouchers
(
    voucher_id          BINARY(16) PRIMARY KEY,
    discount_amount     INT NOT NULL,
    voucher_type        INT NOT NULL
);
