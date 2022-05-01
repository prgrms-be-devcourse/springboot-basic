CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    reduction BIGINT NOT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    voucher_type INT NOT NULL
);

CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY NOT NULL,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    last_login_at datetime DEFAULT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE wallet
(
    voucher_id BINARY(16) NOT NULL,
    customer_id BINARY(16)NOT NULL
);
