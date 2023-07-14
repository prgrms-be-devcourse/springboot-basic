INSERT INTO customers(customer_id, name, email)
VALUES (uuid_to_bin(UUID()), 'tester00', 'test00@gmail.com'),
       (uuid_to_bin(UUID()), 'tester01', 'test01@gmail.com'),
       (uuid_to_bin(UUID()), 'tester02', 'test02@gmail.com');

INSERT INTO vouchers (voucher_id, amount, discount, expiration_date)
VALUES (uuid_to_bin(UUID()), 50, 'PERCENT', DATE_ADD(CURRENT_TIMESTAMP(6), INTERVAL 7 DAY)),
       (uuid_to_bin(UUID()), 100000, 'FIXED', DATE_ADD(CURRENT_TIMESTAMP(6), INTERVAL 7 DAY)),
       (uuid_to_bin(UUID()), 80, 'PERCENT', DATE_ADD(CURRENT_TIMESTAMP(6), INTERVAL 7 DAY));