create table voucher
(
    id     varchar(50) not null PRIMARY KEY,
    type   varchar(15),
    amount bigint
);

create table customer
(
    id   varchar(50) not null PRIMARY KEY,
    name varchar(10)
);