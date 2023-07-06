drop table if exists customer cascade;
create table customer
(
    customer_id    binary(10) primary key,
    customer_name  varchar(30) not null,
    customer_email varchar(50) not null
);