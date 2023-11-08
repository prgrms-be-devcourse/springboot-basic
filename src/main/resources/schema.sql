DROP DATABASE IF EXISTS prod;
CREATE DATABASE prod;
USE prod;
CREATE TABLE customers
(
    id    BINARY(16) PRIMARY KEY,
    name  VARCHAR(20) NOT NULL,
    black BOOLEAN     NOT NULL,
    CONSTRAINT name UNIQUE (name)
);
CREATE TABLE vouchers
(
    id             BINARY(16) PRIMARY KEY,
    type           ENUM ('FIXED', 'PERCENT') NOT NULL,
    discount_value VARCHAR(50)               NOT NULL,
    created_at     DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE ownership
(
    voucher_id  BINARY(16) PRIMARY KEY,
    customer_id BINARY(16),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE
);


DROP DATABASE IF EXISTS test;
CREATE DATABASE test;
USE test;
CREATE TABLE customers
(
    id    BINARY(16) PRIMARY KEY,
    name  VARCHAR(20) NOT NULL,
    black BOOLEAN     NOT NULL,
    CONSTRAINT name UNIQUE (name)
);
CREATE TABLE vouchers
(
    id             BINARY(16) PRIMARY KEY,
    type           ENUM ('FIXED', 'PERCENT') NOT NULL,
    discount_value VARCHAR(50)               NOT NULL,
    created_at     DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE ownership
(
    voucher_id  BINARY(16) PRIMARY KEY,
    customer_id BINARY(16),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE
);