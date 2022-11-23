drop table if exists voucher;

create table voucher
(
    voucher_id      BINARY(16) primary key,
    discount_amount INT          not null,
    voucher_type    varchar(255) not null
);