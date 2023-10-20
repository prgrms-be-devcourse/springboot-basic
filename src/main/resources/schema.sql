CREATE TABLE IF NOT EXISTS customers
(
    customer_id BINARY(16) PRIMARY KEY,
    email       VARCHAR(30) UNIQUE    NOT NULL,
    name        VARCHAR(20)           NOT NULL,
    isBlacklist BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id   BINARY(16) PRIMARY KEY,
    value        LONG    NOT NULL,
    voucher_type TINYINT NOT NULL
);