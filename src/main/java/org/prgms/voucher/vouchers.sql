CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    voucher_type varchar(20) NOT NULL,
    amount INT,
    percent INT,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
);
