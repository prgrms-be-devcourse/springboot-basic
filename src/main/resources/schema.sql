CREATE TABLE IF NOT EXISTS vouchers
(
    id     CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    type   VARCHAR(255) NOT NULL,
    amount BIGINT       NOT NULL
);

CREATE TABLE IF NOT EXISTS customers
(
    id          CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name        VARCHAR(255) NOT NULL,
    blacklisted BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS wallets
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    customer_id CHAR(36) NOT NULL,
    voucher_id  BIGINT   NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (id)
);
