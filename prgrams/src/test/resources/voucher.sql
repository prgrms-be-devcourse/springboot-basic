-- auto-generated definition
create table voucher
(
    voucher_id  varchar(36)                               not null
        primary key,
    type       varchar(20)                              not null,
    voucher_value      bigint                                      not null,
    created_at datetime(6) default CURRENT_TIMESTAMP(6) not null
);
