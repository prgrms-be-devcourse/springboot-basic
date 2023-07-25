DROP TABLE IF EXISTS customers;
CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name        varchar(20) NOT NULL,
    email       varchar(50) NOT NULL,
    create_at   datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

INSERT INTO customers(customer_id, name, email)
VALUES (uuid_to_bin(UUID()), 'tester00', 'test00@gmail.com'),
       (uuid_to_bin(UUID()), 'tester01', 'test01@gmail.com'),
       (uuid_to_bin(UUID()), 'tester02', 'test02@gmail.com');



DROP TABLE IF EXISTS vouchers;
CREATE TABLE vouchers
(
    voucher_id        BINARY(16) PRIMARY KEY,
    amount            bigint NOT NULL,
    discount          enum('FIXED', 'PERCENT') NOT NULL,
    registration_date datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    expiration_date   datetime(6) NOT NULL
);

INSERT INTO vouchers (voucher_id, amount, discount, expiration_date)
VALUES (uuid_to_bin(UUID()), 50, 'PERCENT', DATE_ADD(CURRENT_TIMESTAMP(6), INTERVAL 7 DAY)),
       (uuid_to_bin(UUID()), 100000, 'FIXED', DATE_ADD(CURRENT_TIMESTAMP(6), INTERVAL 7 DAY)),
       (uuid_to_bin(UUID()), 80, 'PERCENT', DATE_ADD(CURRENT_TIMESTAMP(6), INTERVAL 7 DAY));
