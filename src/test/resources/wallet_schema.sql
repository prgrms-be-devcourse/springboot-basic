drop table if exists voucher_wallet;
create table voucher_wallet
(
    wallet_id varchar(36) primary key,
    voucher_id     varchar(36) not null,
    customer_id varchar(36) not null,
    foreign key (voucher_id) references voucher (voucher_id),
    foreign key (customer_id) references customer (customer_id)
);