drop table if exists customer cascade;
create table customer
(
    customer_id    binary(10) primary key,
    customer_name  varchar(30) not null,
    customer_email varchar(50) not null
);

create table vouchers
(
    voucher_id  binary(10) primary key,
    discount    integer(10) NOT NULL,
    type        varchar(20) NOT NULL,
    customer_id binary(10),
    foreign key (customer_id) references customer (customer_id)
);