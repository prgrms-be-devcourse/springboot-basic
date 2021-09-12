DROP TABLE IF EXISTS customers, vouchers;

CREATE TABLE customers
(
    customer_id bigint(100) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    last_login_at datetime DEFAULT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id bigint(100) PRIMARY KEY,
    type varchar(20) NOT NULL,
    amount long NOT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    customer_id bigint(100) REFERENCES customers (customer_id) ON UPDATE CASCADE
);
