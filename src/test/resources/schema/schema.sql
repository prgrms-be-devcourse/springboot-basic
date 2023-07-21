create table customers
(
    customer_id varchar(50) not null,
    name        varchar(20) not null,
    black       boolean     not null,

    primary key (customer_id)
);

create table vouchers
(
    voucher_id     varchar(50) not null,
    voucher_type   varchar(20) not null,
    discount_value double      not null,
    created_at     date        not null,

    primary key (voucher_id)
);

create table wallets
(
    wallet_id   varchar(50) not null,
    voucher_id  varchar(50) not null,
    customer_id varchar(50) not null,

    primary key (wallet_id),
    foreign key (voucher_id) references vouchers (voucher_id) on delete cascade on update cascade,
    foreign key (customer_id) references customers (customer_id) on delete cascade on update cascade
)