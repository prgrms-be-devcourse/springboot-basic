INSERT INTO vouchers(voucher_id, value, voucher_type, created_at) VALUES (UUID_TO_BIN('dd6d3f65-2525-4ae3-a1d1-bd3d70f7ca2c'), 500, 'FIXED_AMOUNT', NOW());
INSERT INTO vouchers(voucher_id, value, voucher_type, created_at) VALUES (UUID_TO_BIN('12d538e9-a838-4c84-ba06-50e9b999c9e0'), 99, 'PERCENT_DISCOUNT', NOW());
INSERT INTO vouchers(voucher_id, value, voucher_type, created_at) VALUES (UUID_TO_BIN('4fab4016-f315-42f0-8870-f2fd04f2995f'), 11, 'PERCENT_DISCOUNT', '2022-05-04');

INSERT INTO customers(customer_id, name, customer_type) VALUES (UUID_TO_BIN('4fab4016-f315-42f0-8870-f2fd04f2995f'), 'black', 'BLACK_LIST');
INSERT INTO customers(customer_id, name, customer_type) VALUES (UUID_TO_BIN('715ddb14-6f1e-4118-aa3d-fbf577513d22'), 'gray', 'BLACK_LIST');
INSERT INTO customers(customer_id, name, customer_type) VALUES (UUID_TO_BIN('94aef515-b5de-432a-a793-abc2aeec3405'), 'white', 'NORMAL');
INSERT INTO customers(customer_id, name, customer_type) VALUES (UUID_TO_BIN('f0a8642b-ad72-443d-ada7-782b5e93f40b'), 'yellow', 'NORMAL');