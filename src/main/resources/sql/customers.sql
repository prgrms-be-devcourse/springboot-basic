CREATE TABLE customers
(
    customer_id   varchar(20) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime             DEFAULT NULL,
    created_at    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);
INSERT INTO customers(customer_id, name, email)
VALUES (100, 'tester00', 'test00@gmail.com');
INSERT INTO customers(customer_id, name, email)
VALUES (101, 'tester01', 'test01@gmail.com');
INSERT INTO customers(customer_id, name, email)
VALUES (102, 'tester02', 'test02@gmail.com');
INSERT INTO customers(customer_id, name, email)
VALUES (10, 'test-user', 'test1-user@gmail.com');
