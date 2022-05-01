INSERT INTO customers(customer_id,name,email,last_login_at,created_at) VALUES (UUID_TO_BIN('98bcb05e-c64b-11ec-8ac0-86fc60ea758b'),'tester00', 'abc00@naver',null, CURRENT_TIMESTAMP);
INSERT INTO customers(customer_id,name,email,last_login_at,created_at) VALUES (UUID_TO_BIN('a5140190-c64b-11ec-8ac0-86fc60ea758b'),'tester01', 'abc01@naver',null, CURRENT_TIMESTAMP);
INSERT INTO customers(customer_id,name,email,last_login_at,created_at) VALUES (UUID_TO_BIN('acb8ea46-c64b-11ec-8ac0-86fc60ea758b'),'tester02', 'abc02@naver',null, CURRENT_TIMESTAMP);

INSERT INTO vouchers(voucher_id,type,discount_info,created_at) VALUES (UUID_TO_BIN('a3a65384-c5e8-11ec-8ac0-86fc60ea758b'),1,1110,CURRENT_TIMESTAMP);
INSERT INTO vouchers(voucher_id,type,discount_info,created_at) VALUES (UUID_TO_BIN('b981ec04-c5e8-11ec-8ac0-86fc60ea758b'),2,50,CURRENT_TIMESTAMP);
INSERT INTO vouchers(voucher_id,type,discount_info,created_at) VALUES (UUID_TO_BIN('c35a0720-c5e8-11ec-8ac0-86fc60ea758b'),2,10,CURRENT_TIMESTAMP);

INSERT INTO customer_has_vouchers(id,customer_id, voucher_id,created_at) VALUES (UUID_TO_BIN(UUID()),UUID_TO_BIN('acb8ea46-c64b-11ec-8ac0-86fc60ea758b'), UUID_TO_BIN('a3a65384-c5e8-11ec-8ac0-86fc60ea758b'), CURRENT_TIMESTAMP);
INSERT INTO customer_has_vouchers(id,customer_id, voucher_id,created_at) VALUES (UUID_TO_BIN(UUID()),UUID_TO_BIN('acb8ea46-c64b-11ec-8ac0-86fc60ea758b'), UUID_TO_BIN('b981ec04-c5e8-11ec-8ac0-86fc60ea758b'), CURRENT_TIMESTAMP);