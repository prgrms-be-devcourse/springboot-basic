CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    reduction BIGINT NOT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    voucher_type INT NOT NULL
);