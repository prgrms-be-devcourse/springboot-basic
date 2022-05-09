CREATE TABLE vouchers
(
    voucher_id     BINARY(16) PRIMARY KEY,
    voucher_amount int         NOT NULL,
    voucher_type   varchar(20) NOT NULL,
    created_at     datetime    NOT NULL DEFAULT current_timestamp()
);
CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime             DEFAULT NULL,
    created_at    datetime    NOT NULL DEFAULT current_timestamp(),
    CONSTRAINT unq_user_email UNIQUE (email)
);