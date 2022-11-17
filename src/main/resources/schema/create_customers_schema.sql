CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL,
    createdAt   datetime,
    CONSTRAINT unq_user_email UNIQUE (email)
);