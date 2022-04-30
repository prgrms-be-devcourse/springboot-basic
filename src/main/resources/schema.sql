create table vouchers
(
    voucher_id binary(16)  not null primary key,
    type       VARCHAR(50) not null,
    value      BIGINT      not null,
    created_at datetime(6) NOT NULL
);

CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    email       varchar(50) NOT NULL,
    name        varchar(50) NOT NULL,
    type        varchar(50) NOT NULL
);

# create table voucher_wallet
# (
#     wallet_id     binary(16)  not null primary key,
#     customer_id   binary(16)  not null,
#     voucher_id    binary(16)  not null,
#     created_at    datetime(6) NOT NULL,
#     expiration_at datetime(6) NOT NULL,
#     FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id),
#    FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
# );


