CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id varchar(255) PRIMARY KEY,
    voucher_type varchar(255),
    discount bigint
);
