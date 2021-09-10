INSERT INTO customers(customer_id, name, email)
VALUES (1, '심수현', 'test00@gmail.com');

INSERT INTO customers(customer_id, name, email)
VALUES (2, '김수현', 'test01@gmail.com');

INSERT INTO vouchers(voucher_id, type, amount, customer_id)
VALUES (1, 'FIXED_AMOUNT', 200, 1);

INSERT INTO vouchers(voucher_id, type, amount, customer_id)
VALUES (2, 'PERCENT_DISCOUNT', 10, null);
