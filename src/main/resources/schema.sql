drop table if exists customers CASCADE;
create table customers
(
    customer_id bigint auto_increment,
    name varchar(255),
    primary key (customer_id)
);

drop table if exists vouchers CASCADE;
create table vouchers
(
    voucher_id bigint auto_increment,
    type varchar(255),
    amount bigint,
    customer_id bigint,
    primary key (voucher_id)
);