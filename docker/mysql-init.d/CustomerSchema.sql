CREATE TABLE customers
(
    customer_id VARCHAR(50) PRIMARY KEY,
    name        VARCHAR(20) NOT NULL,
    email       VARCHAR(50) NOT NULL,
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email   UNIQUE (email)
);
