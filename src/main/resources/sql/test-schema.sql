create table customers
(
    customer_id   binary(16) primary key,
    name          varchar(20) not null,
    email         varchar(50) not null,
    last_login_at datetime             default null,
    created_at    datetime    not null default current_timestamp,
    constraint unq_user_email unique (email)
);

create table vouchers
(
    voucher_id      binary(16) primary key,
    voucher_type    varchar(30) not null,
    discount_amount bigint      not null,
    customer_id     binary(16),
    created_at      datetime    not null default current_timestamp,
    foreign key (customer_id) references customers (customer_id)
);
