CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6) DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

INSERT INTO customers(customer_id, name, email)
VALUES (UUID_TO_BIN('9cbb3d0a-158a-11ec-82a8-0242ac130003'), 'test00', 'test00@gmail.com');
INSERT INTO customers(customer_id, name, email)
VALUES (UUID_TO_BIN('9cbb3f80-158a-11ec-82a8-0242ac130003'), 'test01', 'test01@gmail.com');
INSERT INTO customers(customer_id, name, email)
VALUES (UUID_TO_BIN('9cbb4228-158a-11ec-82a8-0242ac130003'), 'test02', 'test02@gmail.com');

