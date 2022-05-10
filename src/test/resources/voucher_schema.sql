drop table if exists voucher;
create table voucher(
    voucher_id   bigint primary key auto_increment,
    voucher_type varchar(20) not null,
    amount       int         not null,
    created_at   datetime default now()
);