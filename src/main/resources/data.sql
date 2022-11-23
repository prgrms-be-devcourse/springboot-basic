create database if not exists kdt;

use kdt;

create table if not exists voucher(
    id BIGINT not null auto_increment primary key,
    discount_amount DOUBLE(7,2) not null,
    voucher_type VARCHAR(255)
) engine=MyISAM;

create table if not exists customer(
    id BIGINT not null auto_increment primary key,
    email VARCHAR(255) not null,
) engine=MyISAM;