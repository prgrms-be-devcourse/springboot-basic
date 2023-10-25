
CREATE TABLE IF NOT EXISTS customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        VARCHAR(20)           NOT NULL,
    email       VARCHAR(50) UNIQUE    NOT NULL,
    isBlackList BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id     BINARY(16) PRIMARY KEY,
    discount_type  VARCHAR(10) NOT NULL,
    discount_value LONG        NOT NULL
);

CREATE TABLE IF NOT EXISTS wallet
(
    wallet_id   BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) NOT NULL,
    voucher_id  BINARY(16) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id) ON DELETE CASCADE
);


