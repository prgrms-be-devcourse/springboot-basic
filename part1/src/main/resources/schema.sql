CREATE TABLE customers
(
    customer_id    BINARY(16) PRIMARY KEY,
    name           varchar(20) NOT NULL,
    email          varchar(50) NOT NULL,
    created_at     datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_login_at     datetime(6) DEFAULT NULL,
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id    BINARY(16) PRIMARY KEY,
    voucher_type  varchar(30) NOT NULL,
    voucher_amount int NOT NULL,
    created_at     datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_login_at     datetime(6) DEFAULT NULL
);

create table voucher_wallets
(
    voucher_wallet_id BINARY(16) PRIMARY KEY,
    customer_id       BINARY(16) NOT NULL,
    voucher_id        BINARY(16) NOT NULL,
    CONSTRAINT customer_id UNIQUE (customer_id, voucher_id),
    CONSTRAINT voucher_wallets_1 FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE,
    CONSTRAINT voucher_wallets_2 FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id) ON DELETE CASCADE
);