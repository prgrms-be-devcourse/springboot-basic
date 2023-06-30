drop table if exists tbl_vouchers;
drop table if exists tbl_members;

create table tbl_members(
    member_id     varchar(50) not null unique,
    member_status varchar(50) not null,

    primary key (member_id)
);

create table tbl_vouchers
(
    voucher_id     varchar(50) not null unique,
    voucher_amount int         not null,
    voucher_policy varchar(50) not null,
    member_id      varchar(50),

    primary key (voucher_id),
    foreign key (member_id) references tbl_members (member_id)
);
