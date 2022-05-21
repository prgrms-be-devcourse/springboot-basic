create table vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    value bigint NOT NULL,
    type VARCHAR(30) NOT NULL,
    created_at datetime(6) not null
);

create table customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(50) NOT NULL,
    created_at datetime(6) not null
);