drop table if exists member_table;
drop table if exists voucher_table;

create table member_table
(
    member_id     varchar(60) not null,
    member_status varchar(10) not null,
    name          varchar(20) not null,

    primary key (member_id)
);

create table voucher_table
(
    voucher_id    varchar(60) not null,
    voucher_value int         not null,
    voucher_type  varchar(20) not null,

    primary key (voucher_id)
);



