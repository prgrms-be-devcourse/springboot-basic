create table vouchers
(
    voucher_id   binary(16) primary key,
    voucher_type varchar(20),
    quantity     int
);


CREATE TABLE customers (
    customer_id   binary(16) primary key,
    name          varchar(28) not null,
    email         varchar(50) not null,
    last_login_at datetime(6)          default null,
    created_at    datetime(6) not null default current_timestamp(6),
    constraint unique_user_email unique (email)
);