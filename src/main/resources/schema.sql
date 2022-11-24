create table spring_basic.customers
(
    customer_id bigint auto_increment
        primary key,
    name        varchar(20)                              not null,
    email       varchar(50)                              not null,
    created_at  datetime(6) default CURRENT_TIMESTAMP(6) not null,
    constraint unq_user_email
        unique (email)
);
create table spring_basic.vouchers
(
    voucher_id  BINARY(16)                                     not null primary key,
    type        VARCHAR(15)                                           not null,
    amount      BIGINT(11)                                     not null,
    constraint unq_voucher_id unique(voucher_id)
);