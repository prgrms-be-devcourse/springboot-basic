
CREATE TABLE voucher
(
    voucher_id   BINARY(16) PRIMARY KEY,
    voucher_type varchar(50) NOT NULL,
    discount     int         NOT NULL,
    created_at   datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);
CREATE TABLE customerDto
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    customer_type varchar(50) NOT NULL,
    last_login_at datetime(6)          DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_customer_email UNIQUE (email)
);
CREATE TABLE wallet
(
    wallet_id   BINARY(16) PRIMARY KEY,
    customer_id BINARY(16),
    voucher_id  BINARY(16),
    created_at  datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    is_used     boolean              DEFAULT false,
    FOREIGN KEY (customer_id) REFERENCES customerDto (customer_id),
    FOREIGN KEY (voucher_id) REFERENCES voucher (voucher_id),
    CONSTRAINT unq_customer_voucher UNIQUE (customer_id, voucher_id)
);

