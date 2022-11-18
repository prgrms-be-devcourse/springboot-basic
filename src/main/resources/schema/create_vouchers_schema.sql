CREATE TABLE vouchers
(
    voucher_id      BINARY(16) PRIMARY KEY,
    discount_amount int         NOT NULL,
    voucher_type    varchar(50) NOT NULL,
    customer_id     BINARY(16) NOT NULL
);