CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6)          DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    is_banned     boolean     NOT NULL DEFAULT FALSE,
    CONSTRAINT unq_user_email UNIQUE (email)
);

INSERT INTO customers(customer_id,name,email) VALUES (UUID_TO_BIN(UUID()),'test1','test1@gmail.com');
INSERT INTO customers(customer_id,name,email) VALUES (UUID_TO_BIN(UUID()),'test2','test2@gmail.com');
INSERT INTO customers(customer_id,name,email) VALUES (UUID_TO_BIN(UUID()),'test3','test3@gmail.com');
