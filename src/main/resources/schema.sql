drop table if exists customers cascade;
drop table if exists vouchers cascade;

create table customers
(
    id    varchar(36) primary key,
    name  varchar(20) not null,
    age int
);

create table vouchers
(
    id varchar(36) primary key,
    amount int,
    discountType varchar(7) not null
);
