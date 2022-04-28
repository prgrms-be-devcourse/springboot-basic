CREATE TABLE vouchers(
    voucher_id   BINARY(16) PRIMARY KEY,
    voucher_type          varchar(20) NOT NULL,
    discount_amount       long NOT NULL
);