#use order_mgmt;
#drop table vouchers;
CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    voucher_type varchar(20) NOT NULL,
    amount BIGINT NOT NULL,
    usage_status varchar(5) DEFAULT 'N' NOT NULL # 소프트 딜리트 VS 하드 딜리트
);

#INSERT INTO vouchers(voucher_id, voucher_type, amount) VALUES (UUID_TO_BIN(UUID()), 'aaa', 10);
#select * from vouchers;