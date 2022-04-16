CREATE TABLE customers
(
    customer_id    BINARY(16) PRIMARY KEY,
    name           varchar(20) NOT NULL,
    email          varchar(50) NOT NULL,
    created_at     datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id    BINARY(16) PRIMARY KEY,
    voucher_type  varchar(100) NOT NULL,
    voucher_amount int NOT NULL
);

CREATE TABLE voucher_wallets
(
    voucher_wallet_id    BINARY(16) PRIMARY KEY,
    voucher_wallet_name varchar(100) NOT NULL,
    customer_id BINARY(16) NOT NULL,
    voucher_id  BINARY(16) NOT NULL,
    foreign key(customer_id) references customers(customer_id) ON DELETE CASCADE,
    foreign key(voucher_id) references vouchers(voucher_id) ON DELETE CASCADE
);