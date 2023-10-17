CREATE TABLE IF NOT EXISTS customers
(
    customer_id BINARY(16) PRIMARY KEY,
    email       VARCHAR(30) UNIQUE    NOT NULL,
    name        VARCHAR(20)           NOT NULL,
    isBlacklist BOOLEAN DEFAULT FALSE NOT NULL
);