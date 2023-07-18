CREATE TABLE voucher
(
    voucher_id     VARCHAR(36) PRIMARY KEY,
    voucher_type   VARCHAR(50)    NOT NULL,
    discount_value DECIMAL(10, 2) NOT NULL
);

CREATE TABLE customer
(
    email        VARCHAR(100) PRIMARY KEY,
    nickname     VARCHAR(100),
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE customer_voucher_wallet
(
    customer_id VARCHAR(100) NOT NULL,
    voucher_id  VARCHAR(36)  NOT NULL,

    FOREIGN KEY (customer_id) REFERENCES customer (email) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES voucher (voucher_id) ON DELETE CASCADE,
    PRIMARY KEY (customer_id, voucher_id)
);
