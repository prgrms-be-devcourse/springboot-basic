CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    discount_information INT(20) NOT NULL,
    voucher_type varchar(20) NOT NULL,
    owner_id BINARY(16) DEFAULT NULL
);