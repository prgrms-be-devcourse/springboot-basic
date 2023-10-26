CREATE TABLE IF NOT EXISTS customer
(
    id             BINARY(16)   NOT NULL,
    name           VARCHAR(255) NOT NULL,
    is_blacklisted TINYINT(1)   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS voucher
(
    id             BINARY(16) PRIMARY KEY,
    voucher_type   ENUM ('FIXED', 'PERCENT') NOT NULL,
    discount_value BIGINT                    NOT NULL,
    customer_id    BINARY(16),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);
