CREATE TABLE customers
(
    customer_id     BINARY(16) PRIMARY KEY,
    name            varchar(20) NOT NULL,
    email           varchar(50) NOT NULL,
    customer_type   enum('BASIC','BLACKLIST') NOT NULL,
    last_login_at   datetime DEFAULT NULL,
    created_at      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id      BINARY(16) PRIMARY KEY,
    customer_id     BINARY(16) DEFAULT NULL,
    amount          long NOT NULL,
    voucher_type    ENUM('FIXED','PERCENT') NOT NULL,
    created_at      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);