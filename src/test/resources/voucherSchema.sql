CREATE TABLE vouchers
(
    voucher_id BIGINT PRIMARY KEY,
    amount int NOT NULL,
    voucher_type varchar(20) NOT NULL
)