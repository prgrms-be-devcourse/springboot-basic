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
    voucher_type TINYINT NOT NULL,
    created_at   DATE    NOT NULL
);

CREATE TABLE IF NOT EXISTS wallets
(
    wallet_id  INT PRIMARY KEY AUTO_INCREMENT,
    email      VARCHAR(30) NOT NULL,
    voucher_id BINARY(16)  NOT NULL,
    FOREIGN KEY (email) REFERENCES customers (email) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id) ON DELETE CASCADE
);