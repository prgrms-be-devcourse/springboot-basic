CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL,
    created_at  datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id     BINARY(16) PRIMARY KEY,
    voucher_type   varchar(100) NOT NULL,
    voucher_amount int          NOT NULL,
    created_at     datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);

create table voucher_wallets
(
    voucher_wallet_id binary(16) not null
        primary key,
    customer_id       binary(16) not null,
    voucher_id        binary(16) not null,
    constraint customer_id
        unique (customer_id, voucher_id),
    constraint voucher_wallets_ibfk_1
        foreign key (customer_id) references customers (customer_id)
            on delete cascade,
    constraint voucher_wallets_ibfk_2
        foreign key (voucher_id) references vouchers (voucher_id)
            on delete cascade
);

