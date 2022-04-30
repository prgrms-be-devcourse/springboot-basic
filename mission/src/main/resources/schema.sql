-- for mysql
create table voucher
(
    voucher_id     bigint primary key auto_increment,
    discount_value decimal(20) not null,
    voucher_type   varchar(50) not null
);