INSERT INTO customers(customer_id, name, email)
VALUES (UUID_TO_BIN(UUID()), 'tester00', 'test00@gmail.com');
INSERT INTO customers(customer_id, name, email)
VALUES (UUID_TO_BIN(UUID()), 'tester01', 'test01@gmail.com');
INSERT INTO customers(customer_id, name, email)
VALUES (UUID_TO_BIN(UUID()), 'tester02', 'test02@gmail.com');

INSERT INTO vouchers(voucher_id, voucher_type, discount, created_at)
VALUES (UUID_TO_BIN('5129a45c-6ec7-4158-8be0-9f24c3c110f1'), 'FIXED', 3000, CURRENT_TIMESTAMP);
INSERT INTO vouchers(voucher_id, voucher_type, discount, created_at)
VALUES (UUID_TO_BIN('6b20f733-628c-431e-b8ae-d76b81175554'), 'PERCENT', 30, CURRENT_TIMESTAMP);
INSERT INTO vouchers(voucher_id, voucher_type, discount, created_at)
VALUES (UUID_TO_BIN('c9cf01c2-fbd1-4dfa-86d3-46c745892e60'), 'FIXED', 5000, CURRENT_TIMESTAMP);
