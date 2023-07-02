CREATE TABLE customers
(
    customer_id    BIGINT PRIMARY KEY,
    name           varchar(20) NOT NULL,
    email          varchar(50) NOT NULL,
    last_login_at  datetime             DEFAULT NULL,
    created_at     datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);
--
-- INSERT INTO customers (customer_id, name, email) VALUES (UUID(), 'tester00', 'test00@gmail.com');
-- INSERT INTO customers (customer_id, name, email) VALUES (UUID(), 'tester01', 'test01@gmail.com');
-- INSERT INTO customers (customer_id, name, email) VALUES (UUID(), 'tester02', 'test02@gmail.com');

