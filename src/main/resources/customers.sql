# drop table customers;
# select * from customers;

CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    last_login_at datetime(6) DEFAULT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

# INSERT INTO customers(customer_id,name,email) VALUES (UUID_TO_BIN(UUID()),'tester00','test00@gmail.com');
# INSERT INTO customers(customer_id,name,email) VALUES (UUID_TO_BIN(UUID()),'tester01','test01@gmail.com');
# INSERT INTO customers(customer_id,name,email) VALUES (UUID_TO_BIN(UUID()),'tester02','test02@gmail.com');

# select * from customers;
# DELETE from customers ;