drop table if exists voucher;
create table voucher(
    voucher_id   varchar(36) not null primary key,
    voucher_type varchar(20) not null,
    amount       int         not null,
    created_at   datetime default now()
);