create table vouchers
(
    voucher_id      binary(16) primary key,
    voucher_type    varchar(30) not null,
    discount_amount bigint      not null,
    customer_id     binary(16),
    created_at      datetime    not null default current_timestamp,
    foreign key (customer_id) references customers (customer_id)
);