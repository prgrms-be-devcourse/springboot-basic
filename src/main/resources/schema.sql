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
    voucher_id  BINARY(16) PRIMARY KEY,
    customer_id BINARY(16),
    type        TINYINT UNSIGNED NOT NULL,
    discount    INT UNSIGNED     NOT NULL
);
