CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
        name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    is_black boolean NOT NULL DEFAULT FALSE,
    last_login_at datetime DEFAULT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);
CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    voucher_type varchar(20) NOT NULL,
    percent INTEGER,
    amount INTEGER,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);