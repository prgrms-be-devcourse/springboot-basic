create table customers
(
    customer_id varchar(50) not null,
    name        varchar(20) not null,
    black       boolean     not null,

    primary key (customer_id)
);

create table vouchers
(
    voucher_id     varchar(50) not null,
    voucher_type   varchar(20) not null,
    discount_value double      not null,
    created_at     datetime(6) not null,
    customer_id    varchar(50) default null,

    primary key (voucher_id),
    foreign key (customer_id) references customers (customer_id)
);