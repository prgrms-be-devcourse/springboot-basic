CREATE TABLE customer
(
    id         BINARY(16) PRIMARY KEY,
    name       varchar(20) NOT NULL UNIQUE,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    is_banned  boolean     NOT NULL DEFAULT FALSE,
    CONSTRAINT unq_user_name UNIQUE (name)
);

CREATE TABLE voucher
(
    id              BINARY(16) PRIMARY KEY,
    name            varchar(20) NOT NULL UNIQUE,
    discount_amount float       NOT NULL DEFAULT FALSE,
    created_at      datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_voucher_name UNIQUE (name)
);

CREATE TABLE wallet
(
    id          BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) NOT NULL,
    voucher_id  BINARY(16) NOT NULL
);
