create table vouchers
(
    voucher_id     binary(16)  primary key,
    voucher_type   varchar(20) not null,
    discount_value double      not null
);

