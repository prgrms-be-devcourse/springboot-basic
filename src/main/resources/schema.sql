drop table if exists tbl_wallets;
drop table if exists tbl_vouchers;
drop table if exists tbl_members;

create table tbl_members
(
    member_id     varchar(50) not null unique,
    member_status varchar(50) not null,

    primary key (member_id)
);

create table tbl_vouchers
(
    voucher_id     varchar(50) not null unique,
    voucher_amount int         not null,
    voucher_policy varchar(50) not null,

    primary key (voucher_id)
);

create table tbl_wallets
(
    wallet_id  varchar(50) not null unique,
    voucher_id varchar(50) unique ,
    member_id  varchar(50) not null,

    foreign key (voucher_id)
        references tbl_vouchers (voucher_id)
        on delete cascade on update cascade,
    foreign key (member_id)
        references tbl_members (member_id)
        on delete cascade on update cascade
);

INSERT INTO tbl_vouchers (voucher_id, voucher_amount, voucher_policy)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 10, 'FIXED_DISCOUNT'),
    ('550e8400-e29b-41d4-a716-446655440002', 20, 'FIXED_DISCOUNT'),
    ('550e8400-e29b-41d4-a716-446655440003', 30, 'PERCENT_DISCOUNT'),
    ('550e8400-e29b-41d4-a716-446655440004', 30, 'PERCENT_DISCOUNT'),
    ('550e8400-e29b-41d4-a716-446655440005', 30, 'PERCENT_DISCOUNT');

INSERT INTO tbl_members (member_id, member_status)
VALUES
    ('550e8400-e29b-41d4-a716-446655440004', 'BLACK'),
    ('550e8400-e29b-41d4-a716-446655440005', 'WHITE'),
    ('550e8400-e29b-41d4-a716-446655440006', 'WHITE');

INSERT INTO tbl_wallets (wallet_id, voucher_id, member_id)
VALUES
    ('550e8400-e29b-41d4-a716-446655440007', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440004'),
    ('550e8400-e29b-41d4-a716-446655440008', '550e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440004'),
    ('550e8400-e29b-41d4-a716-446655440009', '550e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440004'),
    ('550e8400-e29b-41d4-a716-446655440010', '550e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440005');
