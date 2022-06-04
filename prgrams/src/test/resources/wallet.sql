create table wallet
(
    wallet_id  varchar(36)                               not null
        primary key,
    voucher_id       varchar(36)                         not null,
    customer_id      varchar(36)                         not null,
    created_at datetime(6) default CURRENT_TIMESTAMP(6) not null
);
