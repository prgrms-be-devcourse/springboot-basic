INSERT INTO customers(customer_id,name) VALUES (UUID_TO_BIN('98bcb05e-c64b-11ec-8ac0-86fc60ea758b'),'tester00');
INSERT INTO customers(customer_id,name) VALUES (UUID_TO_BIN('a5140190-c64b-11ec-8ac0-86fc60ea758b'),'tester01');
INSERT INTO customers(customer_id,name) VALUES (UUID_TO_BIN('acb8ea46-c64b-11ec-8ac0-86fc60ea758b'),'tester02');

INSERT INTO vouchers(voucher_id,type,discount_info,created_at) VALUES (UUID_TO_BIN('a3a65384-c5e8-11ec-8ac0-86fc60ea758b'),'fixed',1110,CURRENT_TIMESTAMP);

INSERT INTO vouchers(voucher_id,type,discount_info,created_at) VALUES (UUID_TO_BIN('b981ec04-c5e8-11ec-8ac0-86fc60ea758b'),'percent',50,CURRENT_TIMESTAMP);
INSERT INTO vouchers(voucher_id,type,discount_info,created_at) VALUES (UUID_TO_BIN('c35a0720-c5e8-11ec-8ac0-86fc60ea758b'),'percent',10,CURRENT_TIMESTAMP);

INSERT INTO customer_has_vouchers(customer_id, voucher_id) VALUES (UUID_TO_BIN('acb8ea46-c64b-11ec-8ac0-86fc60ea758b'), UUID_TO_BIN('a3a65384-c5e8-11ec-8ac0-86fc60ea758b'));
