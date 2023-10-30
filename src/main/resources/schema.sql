CREATE TABLE voucher
(
    id   BINARY(16)  PRIMARY KEY,
    type VARCHAR(16) NOT NULL,
    data INT         NOT NULL
);

CREATE TABLE customer
(
    id            BINARY(16)  PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6) DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

create table wallet
(
    customer_id BINARY(16)    NOT NULL,
    voucher_id  BINARY(16)    NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id)  REFERENCES voucher(id)  ON DELETE CASCADE,
    PRIMARY KEY (customer_id, voucher_id)
);
