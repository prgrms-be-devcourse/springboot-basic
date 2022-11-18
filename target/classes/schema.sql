use test_voucher_mgmt;
drop table if exists customers;
CREATE TABLE customers
(
    customer_id     BINARY(16)  PRIMARY KEY,
    name            varchar(20) NOT NULL,
    email           varchar(50) NOT NULL,
    last_login_at   datetime(6) DEFAULT NULL,
    created_at      datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

drop table if exists vouchers;
CREATE TABLE vouchers
(
    voucher_id      BINARY(16)  PRIMARY KEY,
    type            varchar(50) NOT NULL,
    discount        int NOT NULL
);

create table wallet
(
    wallet_id   BINARY(16) not null,
    customer_id BINARY(16) not null,
    voucher_id  BINARY(16) not null,
    constraint wallet_pk
        primary key (wallet_id),
    constraint wallet_customers_customer_id_fk
        foreign key (customer_id) references customers (customer_id)
            on update cascade on delete cascade,
    constraint wallet_vouchers_voucher_id_fk
        foreign key (voucher_id) references vouchers (voucher_id)
            on update cascade on delete cascade
);

create unique index wallet_customer_id_uindex
    on wallet (customer_id);

create unique index wallet_voucher_id_uindex
    on wallet (voucher_id);

create unique index wallet_wallet_id_uindex
    on wallet (wallet_id);