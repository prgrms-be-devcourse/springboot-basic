CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id   VARCHAR(36) PRIMARY KEY,
    voucher_type VARCHAR(10) NOT NULL,
    discount     LONG        NOT NULL,
    created_at   TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS customers
(
    customer_id      VARCHAR(36) PRIMARY KEY,
    customer_type    VARCHAR(10) NOT NULL,
    created_at       TIMESTAMP   NOT NULL,
    last_modified_at TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS wallets
(
    voucher_id  VARCHAR(36) NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    created_at  TIMESTAMP   NOT NULL,
    PRIMARY KEY (voucher_id, customer_id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE
);