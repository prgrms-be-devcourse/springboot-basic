drop table if exists customer_has_vouchers;
drop table if exists customers;
drop table if exists vouchers;

CREATE TABLE customers
(
    id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6)          DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    id    BINARY(16) PRIMARY KEY,
    type          TINYINT NOT NULL,
    discount_info int     NOT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
);

CREATE TABLE customer_has_vouchers
(
    id          BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) NOT NULL,
    voucher_id  BINARY(16) NOT NULL,
    created_at  datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (id)
);