create table vouchers
(
    voucher_id   int auto_increment primary key,
    voucher_type varchar(20)                              not null,
    value        int                                      not null,
    created_at   datetime(6) default CURRENT_TIMESTAMP(6) null,
    updated_at   datetime(6) default CURRENT_TIMESTAMP(6) null
);
