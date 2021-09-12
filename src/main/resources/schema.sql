CREATE TABLE customers
(
    customer_id  BINARY(16) PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL,
    type        varchar(20) NOT NULL,
    last_login_at   datetime(6)            DEFAULT NULL,
    created_at       datetime(6) NOT NULL   DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id  BINARY(16) PRIMARY KEY,
    name           varchar(50) NOT NULL,
    type        ENUM('F', 'P') NOT NULL,
    discount    int(6) NOT NULL,
    used_at   datetime(6)            DEFAULT NULL,
    created_at       datetime(6) NOT NULL   DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE wallets
(
    wallet_id    BINARY(16) PRIMARY KEY,
    customer_id       BINARY(16),
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
    created_at       datetime(6) NOT NULL   DEFAULT CURRENT_TIMESTAMP(6)
);