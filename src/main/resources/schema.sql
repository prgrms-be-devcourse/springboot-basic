create table voucher
(
    id     VARCHAR(50) not null PRIMARY KEY ,
    type   VARCHAR(15),
    amount bigint,
    date   timestamp not null default current_timestamp
);
create table customer
(
    id   varchar(50) not null PRIMARY KEY,
    name varchar(10)
);
