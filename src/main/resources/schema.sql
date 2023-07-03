drop table if exists customers cascade;
create table customers
(
    customer_id    binary(10) primary key,
    customer_name  varchar(30) not null,
    customer_email varchar(50) not null,
    customer_type  varchar(20) not null
);
