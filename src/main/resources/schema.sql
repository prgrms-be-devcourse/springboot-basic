-- DROP TABLE customers,vouchers;

CREATE TABLE customers
(
    customer_id BIGINT PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    last_login_at TIMESTAMP DEFAULT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unq_user_email UNIQUE (email)
);

INSERT INTO customers (customer_id, name, email) VALUES (10, 'kim','kim@gmail.com');

CREATE TABLE vouchers
(
    voucher_id BIGINT PRIMARY KEY,
    voucher_type varchar(20) NOT NULL,
    discount_amount double DEFAULT NULL
);

INSERT INTO vouchers (voucher_id, voucher_type, discount_amount) VALUES (100, 'FIXED', 10000);
INSERT INTO vouchers (voucher_id, voucher_type, discount_amount) VALUES (1000, 'PERCENT', 30);

