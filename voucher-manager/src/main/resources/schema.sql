CREATE TABLE voucher
(
    voucher_id     VARCHAR(36) PRIMARY KEY,
    voucher_type   VARCHAR(50),
    discount_value DECIMAL(10, 2)
);
