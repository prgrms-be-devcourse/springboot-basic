CREATE TABLE customer
(
    id            BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    type          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE voucher
(
    id         BINARY(16) PRIMARY KEY,
    name       varchar(20) NOT NULL,
    type       varchar(20) NOT NULL,
    figure     int         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE voucher_wallet
(
    id          BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) NOT NULL,
    voucher_id  BINARY(16) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES voucher (id) ON DELETE CASCADE
);