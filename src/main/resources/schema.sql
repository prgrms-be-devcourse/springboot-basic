CREATE TABLE voucher
(
    voucher_id   BINARY(16) PRIMARY KEY,
    voucher_type VARCHAR(20) NOT NULL,
    discount     bigint      NOT NULL
);

CREATE TABLE customer
(
    customer_id BINARY(16) PRIMARY KEY,
    email       VARCHAR(20) UNIQUE NOT NULL,
    name        VARCHAR(20)        NOT NULL,
    created_at  datetime(6) NOT NULL
);