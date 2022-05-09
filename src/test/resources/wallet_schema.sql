drop table if exists voucher_wallet;
create table voucher_wallet
(
    wallet_id   bigint primary key auto_increment,
    voucher_id  bigint not null,
    customer_id bigint not null,
    created_at  datetime default now(),
    foreign key (voucher_id) references voucher (voucher_id),
    foreign key (customer_id) references customer (customer_id)
);