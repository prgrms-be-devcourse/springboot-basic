USE voucher_service;

DROP TABLE IF EXISTS voucher_type;
DROP TABLE IF EXISTS customer_status;
DROP TABLE IF EXISTS wallet;
DROP TABLE IF EXISTS voucher;
DROP TABLE IF EXISTS customer;

CREATE TABLE voucher_type
(
    id   INTEGER PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    UNIQUE KEY unq_type (type)
);

CREATE TABLE customer_status
(
    id     INTEGER     NOT NULL PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    UNIQUE KEY unq_status (status)
);

CREATE TABLE wallet
(
    id         BINARY(16) NOT NULL,
    voucher_id BINARY(16) NOT NULL,
    PRIMARY KEY (id, voucher_id)

);

CREATE TABLE voucher
(
    id         BINARY(16) PRIMARY KEY,
    type_id    INTEGER    NOT NULL,
    amount     LONG       NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    wallet_id  BINARY(16) NOT NULL,
    FOREIGN KEY (type_id) REFERENCES voucher_type (id)
);

ALTER TABLE wallet
    ADD FOREIGN KEY (voucher_id) REFERENCES voucher (id);
ALTER TABLE voucher
    ADD FOREIGN KEY (wallet_id) REFERENCES wallet (id);

CREATE TABLE customer
(
    id        BINARY(16) PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    status_id INTEGER      NOT NULL,
    wallet_id BINARY(16)   NOT NULL,
    FOREIGN KEY (status_id) REFERENCES customer_status (id),
    FOREIGN KEY (wallet_id) REFERENCES wallet (id)
);

INSERT INTO voucher_type (id, type)
VALUES (1, 'FIXED_AMOUNT'),
       (2, 'PERCENT_AMOUNT');

INSERT INTO customer_status (id, status)
VALUES (1, 'WHITE'),
       (2, 'BLACK');

