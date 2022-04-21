-- auto-generated definition
create table customers
(
    customer_id   binary(16)                               not null
        primary key,
    name          varchar(20)                              not null,
    email         varchar(50)                              not null,
    password      varchar(50)                              not null,
    last_login_at datetime(6)                              null,
    created_at    datetime(6) default CURRENT_TIMESTAMP(6) not null,
    constraint unq_user_email
        unique (email)
);

-- auto-generated definition
create table voucher
(
    voucher_id     binary(16)                               not null
        primary key,
    amount         decimal(20)                              not null,
    voucher_type   enum ('FIXED', 'PERCENT')                not null,
    voucher_status varchar(50)                              not null,
    customer_id    binary(16)                               not null,
    created_at     datetime(6) default CURRENT_TIMESTAMP(6) not null
);

