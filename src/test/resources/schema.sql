DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS voucher;

CREATE TABLE customer
(
    customer_id   BINARY(16)   NOT NULL PRIMARY KEY,
    email         VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    created_at    DATETIME(6)  NOT NULL,
    last_login_at DATETIME(6),
    is_deleted    BOOLEAN DEFAULT 0
);

CREATE TABLE voucher
(
    voucher_id      BINARY(16) PRIMARY KEY,
    discount_amount INTEGER DEFAULT 0,
    type            VARCHAR(32) NOT NULL
);
