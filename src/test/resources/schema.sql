create table vouchers
(
    voucher_id        binary(16) primary key,
    voucher_type      varchar(20),
    discount_quantity int,
    discount_ratio    int,
    voucher_count     int,
    created_at        datetime(6),
    ended_at          datetime(6)
);

insert into vouchers(voucher_id, voucher_type, discount_quantity, discount_ratio, voucher_count, created_at, ended_at)
values (UUID_TO_BIN('67c45e69-2523-4097-8100-c8a8de3e2260'), 'FIXED', 100, 0, 1, '2022-11-24',
        DATE_ADD('2022-11-24', INTERVAL 3 DAY));

insert into vouchers(voucher_id, voucher_type, discount_quantity, discount_ratio, voucher_count, created_at, ended_at)
values (UUID_TO_BIN('beb30b22-e81c-4a76-97e4-4a929fc28800'), 'FIXED', 200, 0, 2, '2022-11-24',
        DATE_ADD('2022-11-24', INTERVAL 4 DAY));

insert into vouchers(voucher_id, voucher_type, discount_quantity, discount_ratio, voucher_count, created_at, ended_at)
values (UUID_TO_BIN('fb90b145-8bae-46e5-8600-172fd5491588'), 'PERCENT', 0, 10, 11, '2022-11-24',
        DATE_ADD('2022-11-24', INTERVAL 5 DAY));

insert into vouchers(voucher_id, voucher_type, discount_quantity, discount_ratio, voucher_count, created_at, ended_at)
values (UUID_TO_BIN('4fda8f8c-b249-4dfb-b5f4-9304d2725a97'), 'PERCENT', 0, 20, 22, '2022-11-24',
        DATE_ADD('2022-11-24', INTERVAL 6 DAY));


CREATE TABLE customers
(
    customer_id   binary(16) primary key,
    name          varchar(28) not null,
    email         varchar(50) not null,
    last_login_at datetime(6)          default null,
    created_at    datetime(6) not null default current_timestamp(6),
    constraint unique_user_email unique (email)
);