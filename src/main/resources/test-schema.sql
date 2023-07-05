CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    created_at    datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    CONSTRAINT unq_user_email UNIQUE (email)
);