use voucher_schema;

drop table if exists tbl_members;
drop table if exists tbl_vouchers;

create table tbl_vouchers (
    voucher_id varchar(50) not null unique ,
    voucher_amount int not null ,
    voucher_policy varchar(50) not null ,

    primary key (voucher_id)
);

create table tbl_members (
    member_id varchar(50) not null unique ,
    member_status varchar(50) not null ,
    voucher_id varchar(50) ,

    primary key (member_id) ,
    foreign key (voucher_id) references tbl_vouchers (voucher_id)
);
