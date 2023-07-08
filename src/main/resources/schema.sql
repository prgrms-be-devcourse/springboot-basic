drop table if exists customers cascade;
create table customers
(
    customer_id    varchar(36) primary key,
    customer_name  varchar(30) not null,
    customer_email varchar(50) not null,
    customer_type  varchar(20) not null
);

create table vouchers
(
    voucher_id varchar(36) primary key,
    voucher_discount_amount int not null,
    voucher_type varchar(20) not null
)

create table wallet
(
    wallet_id varchar(36) primary key,
    customer_id varchar(36) not null unique,
    voucher_id varchar(36) not null,
    foreign key (customer_id) references customers(customer_id),
    foreign key (voucher_id) references vouchers(voucher_id),
)
