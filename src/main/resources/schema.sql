CREATE TABLE customers
(
    customer_id    BINARY(16) PRIMARY KEY,
    customer_name  varchar(20) NOT NULL,
    customer_email varchar(50) NOT NULL,
    last_login_at  datetime(6) DEFAULT NULL,
    created_at     datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    CONSTRAINT unq_customer_email UNIQUE (customer_email)
);

CREATE TABLE vouchers
(
    voucher_id      BINARY(16) PRIMARY KEY,
    voucher_type    varchar(30) NOT NULL,
    discount_amount bigint      NOT NULL,
    customer_email  varchar(50),
    created_at      datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    FOREIGN KEY (customer_email) REFERENCES customers (customer_email)
);

CREATE TABLE orders
(
    order_id     BINARY(16) PRIMARY KEY,
    customer_id  BINARY(16),
    order_items  varchar(50) NOT NULL,
    voucher_id   BINARY(16),
    order_status varchar(30) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id)
);