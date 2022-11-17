create database if not exists kdt;

use kdt;

create table if not exists voucher(
    voucher_id BINARY(16) not null
        primary key,
    discount_amount DOUBLE(7,2) not null,
    voucher_type varchar(255)
) engine=MyISAM;