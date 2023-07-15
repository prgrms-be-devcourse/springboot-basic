use voucher;
drop table if exists vouchers;
create table vouchers
(
    voucher_id      bigint primary key auto_increment,
    discount_amount int         not null,
    type            varchar(20) not null
);

drop table if exists customers;
create table customers
(
    customer_id bigint primary key auto_increment,
    name        varchar(10)        not null,
    email       varchar(50) unique not null
);
