-- auto-generated definition
create table voucher
(
    voucher_id     bigint auto_increment primary key,
    discount_value decimal(20) not null,
    voucher_type   varchar(50) not null,
    created_at      datetime(6) not null
);