create table customers
(
    customer_id binary(16)                          not null
        primary key,
    name        varchar(20)                         not null,
    email       varchar(50)                         not null,
    status      varchar(10)                         null,
    created_at  timestamp default CURRENT_TIMESTAMP not null,
    constraint unq_customer_email
        unique (email)
);

create table voucher_wallets
(
    wallet_id   binary(16) not null
        primary key,
    customer_id binary(16) not null,
    voucher_id  binary(16) not null
);

create index foreign_customer_id
    on voucher_wallets (customer_id);

create index foreign_voucher_id
    on voucher_wallets (voucher_id);

create table vouchers
(
    voucher_id binary(16)                            not null
        primary key,
    type       varchar(10) default 'FIX'             not null,
    amount     int         default 0                 not null,
    created_at timestamp   default CURRENT_TIMESTAMP not null
);