CREATE TABLE customers
(
    customer_id     BINARY(16) PRIMARY KEY,
    name            varchar(20) NOT NULL,
    email           varchar(50) NOT NULL,
    last_login_at   datetime(6)             DEFAULT NULL,
    created_at      datetime(6) NOT NULL    DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

DROP TABLE vouchers;
CREATE TABLE vouchers
(
    voucher_id  BINARY(16)  PRIMARY KEY,
    rate        BIGINT      NOT NULL,
    type        varchar(20) NOT NULL,
    customer_id BINARY(16)  NULL,
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
    ON DELETE CASCADE
);

INSERT INTO customers (customer_id, name, email) VALUE (UUID_TO_BIN("2defe9d8-4bb0-4612-ac87-922bbc4bb372"), "sample", "sample@gmail.com");
