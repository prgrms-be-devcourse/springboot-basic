DROP TABLE IF EXISTS wallet;
DROP TABLE IF EXISTS voucher;
DROP TABLE IF EXISTS customer;

CREATE TABLE voucher
(
    voucher_id     VARCHAR(200) NOT NULL,
    discount_value BIGINT       NOT NULL,
    voucher_type   VARCHAR(200) NOT NULL,
    PRIMARY KEY (voucher_id)
);

create table customer
(
    customer_id    VARCHAR(200) NOT NULL,
    customer_name  VARCHAR(200) NOT NULL,
    customer_email VARCHAR(200) NOT NULL,
    customer_type  VARCHAR(200) NOT NULL,
    created_at     TIMESTAMP    NOT NULL,
    PRIMARY KEY (customer_id)
);

create table wallet
(
    wallet_id   VARCHAR(200) NOT NULL,
    customer_id VARCHAR(200) NOT NULL,
    voucher_id  VARCHAR(200) NOT NULL,
    PRIMARY KEY (wallet_id),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES voucher (voucher_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
