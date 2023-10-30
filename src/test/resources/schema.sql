DROP TABLE IF EXISTS wallets;
DROP TABLE IF EXISTS vouchers;
DROP TABLE IF EXISTS customers;

CREATE TABLE vouchers
(
    id     CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    type   VARCHAR(255) NOT NULL,
    amount BIGINT       NOT NULL
);

CREATE TABLE customers
(
    id          CHAR(36) PRIMARY KEY  DEFAULT (UUID()),
    email       VARCHAR(255) NOT NULL UNIQUE,
    blacklisted BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE wallets
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    customer_id CHAR(36) NOT NULL,
    voucher_id  CHAR(36) NOT NULL,
    used        BOOLEAN  NOT NULL DEFAULT FALSE,
    UNIQUE (customer_id, voucher_id)
);
