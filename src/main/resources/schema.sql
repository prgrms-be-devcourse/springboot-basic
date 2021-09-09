CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    last_login_at datetime DEFAULT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    black tinyint(1) DEFAULT 0,
    CONSTRAINT unq_user_email UNIQUE (email)
);





create table vouchers
(
    voucher_id binary(16) not null
        primary key,
    type       int        not null,
    amount     int        not null,
    created_at datetime   null,
    expired_at datetime   null
);





create table wallets
(
    wallet_id   binary(16) not null,
    customer_id binary(16) not null,
    voucher_id  binary(16) not null,
    created_at  datetime   not null,
    used_at     datetime   null,
    constraint wallets_wallet_id_uindex
        unique (wallet_id),
    constraint wallets_ibfk_1
        foreign key (customer_id) references customers (customer_id),
    constraint wallets_ibfk_2
        foreign key (voucher_id) references vouchers (voucher_id)
);

create index customer_id
    on wallets (customer_id);

create index voucher_id
    on wallets (voucher_id);

alter table wallets
    add primary key (wallet_id);

