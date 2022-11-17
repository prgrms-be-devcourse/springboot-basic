CREATE TABLE customer
(
    customer_id   varchar(50) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);