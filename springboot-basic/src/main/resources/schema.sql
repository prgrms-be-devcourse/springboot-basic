drop table if exists voucher cascade;
drop table if exists customer cascade;
create table customer
(
    customer_id    binary(100) primary key,
    customer_name  varchar(30) not null,
    customer_email varchar(50) not null
);

create table voucher
(
    voucher_id       binary(100) primary key,
    voucher_discount integer(10) NOT NULL,
    voucher_type     varchar(20) NOT NULL
#     customer_id binary(10),
#     foreign key (customer_id) references customer (customer_id)
);