-- auto-generated definition
create table customers
(
    customer_id   binary(16)  not null primary key,
    type          varchar(20) not null,
    name          varchar(20) not null unique,
    email         varchar(50) not null,
    last_login_at datetime(6) null,
    constraint unq_user_email
        unique (email)
);

create table vouchers
(
    voucher_id    binary(16)  not null primary key,
    type          varchar(20) not null,
    discount      int         not null,
    used          boolean
);

create table voucher_wallet
(
    seq         bigint auto_increment
        primary key,
    voucher_id  binary(16)  not null,
    customer_id binary(16)  not null,
    create_at   datetime(6) not null,
    constraint voucher_id
        unique (voucher_id),
    constraint fk_order_items_to_order
        foreign key (voucher_id) references vouchers (voucher_id),
    constraint fk_order_items_to_product
        foreign key (customer_id) references customers (customer_id)
            on delete cascade
);

