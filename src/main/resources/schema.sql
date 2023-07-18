create table vouchers
(
    voucher_id varchar(50) primary key,
    discount int not null,
    voucher_type varchar(30) not null,
    voucher_createdAt TIMESTAMP not null
);
create table customers
(
    customer_id varchar(50) primary key,
    customer_name varchar(50) not null,
    customer_email varchar(100) not null,
    customer_createAt TIMESTAMP not null
)
