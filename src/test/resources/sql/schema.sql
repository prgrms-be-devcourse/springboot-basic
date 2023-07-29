drop table if exists wallet_table;
drop table if exists member_table;
drop table if exists voucher_table;

create table member_table
(
    member_id     varchar(60),
    member_status varchar(10) not null,
    name          varchar(20) not null,
    created_at    varchar(20) not null,

    primary key (member_id)
);

create table voucher_table
(
    voucher_id    varchar(60),
    voucher_value int         not null,
    voucher_type  varchar(20) not null,
    created_at    varchar(20) not null,

    primary key (voucher_id)
);

create table wallet_table
(
    wallet_id  varchar(60),
    voucher_id varchar(60) not null,
    member_id  varchar(60) not null,
    created_at    varchar(20) not null,

    primary key (wallet_id),
    foreign key (voucher_id) references voucher_table (voucher_id),
    foreign key (member_id) references member_table (member_id)
);
