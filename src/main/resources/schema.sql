DROP TABLE IF EXISTS voucher;

CREATE TABLE voucher
(
    id   BINARY(16)  PRIMARY KEY,
    type VARCHAR(16) NOT NULL,
    data INT         NOT NULL
)

DROP TABLE IF EXISTS customer;

CREATE TABLE customer
(
    id            BINARY(16)  PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6) DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    CONSTRAINT unq_user_email UNIQUE (email)
);
