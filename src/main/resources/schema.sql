drop table if exists customer cascade;
drop table if exists voucher cascade;
drop table if exists wallet cascade;
create table customer
(
    customer_id    varchar(36) primary key,
    customer_name  varchar(30) not null,
    customer_email varchar(50) not null,
    customer_type  varchar(20) not null
);

create table voucher
(
    voucher_id              varchar(36) primary key,
    voucher_discount_amount int         not null,
    voucher_type            varchar(20) not null
);

create table wallet
(
    wallet_id   varchar(36) primary key,
    customer_id varchar(36) not null unique,
    voucher_id  varchar(36) not null,
    foreign key (customer_id) references customer (customer_id)
        on delete cascade on update cascade,
    foreign key (voucher_id) references voucher (voucher_id)
        on delete cascade on update cascade
);
