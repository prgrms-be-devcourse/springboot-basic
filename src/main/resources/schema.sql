CREATE TABLE customer
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6)          DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE voucher
(
    voucher_id   BINARY(16) PRIMARY KEY,
    amount       INTEGER     NOT NULL,
    customer_id  BINARY(16)  NULL,
    voucher_type varchar(20) NOT NULL,
    created_at   datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_voucher_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);