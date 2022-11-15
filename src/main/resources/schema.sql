CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id   varchar(36) PRIMARY KEY,
    amount       bigint      NOT NULL,
    voucher_type varchar(30) NOT NULL

);


CREATE TABLE IF NOT EXISTS customers
(
    customer_id   varchar(36) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    customer_status varchar(10) NOT NULL DEFAULT 'NORMAL',
    last_login_at datetime             DEFAULT NULL,
    created_at    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);