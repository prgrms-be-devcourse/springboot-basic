CREATE TABLE vouchers
(
    voucher_id     BINARY(16)  PRIMARY KEY,
    type            varchar(20) NOT NULL,
    value           bigint(50) NOT NULL,
    CONSTRAINT      unq_voucher_type   UNIQUE (type, value)
);