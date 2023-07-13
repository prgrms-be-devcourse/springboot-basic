drop table if exists voucher CASCADE;
drop table if exists customer CASCADE;

create table voucher
(
    id     varchar(50) not null unique,
    type   varchar(50) not null,
    amount int         not null,
    primary key (id)
);

create table customer
(
    id       varchar(50) not null unique,
    nickname varchar(50) not null unique,
    primary key (id)
);
