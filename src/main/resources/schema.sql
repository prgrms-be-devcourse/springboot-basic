CREATE TABLE customers (
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    last_login_at datetime DEFAULT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);


CREATE TABLE vouchers (
    voucher_id	BINARY(16) NOT NULL PRIMARY KEY,
    customer_id	BINARY(16) NULL,
    voucher_type char(2) NOT NULL,
    -- discount_percent bigint	NULL DEFAULT 0,
    -- discount_amount	bigint NULL	DEFAULT 0,
    discount bigint NULL	DEFAULT 0,
    use_yn boolean NOT NULL	DEFAULT FALSE,
    created_at datetime	NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    used_at datetime NULL
);
