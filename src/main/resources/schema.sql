CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          VARCHAR(20) NOT NULL,
    email         VARCHAR(50) NOT NULL,
    isBlackList BOOLEAN DEFAULT FALSE NOT NULL
);
