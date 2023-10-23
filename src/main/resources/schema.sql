CREATE TABLE IF NOT EXISTS customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        VARCHAR(20)           NOT NULL,
    email       VARCHAR(50)           NOT NULL,
    isBlackList BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id     BINARY(16) PRIMARY KEY,
    discount_type  VARCHAR(10) NOT NULL,
    discount_value LONG        NOT NULL
);
