create database if not exists test;

use test;

create table if not exists voucher(
    id BIGINT not null auto_increment primary key,
    discount_amount DOUBLE(7,2) not null,
    voucher_type varchar(255)
) engine=MyISAM;
