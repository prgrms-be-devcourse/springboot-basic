create TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    customer_name VARCHAR(30) NOT NULL,
    customer_type VARCHAR(30) NOT NULL
);

insert into customers values(UUID_TO_BIN("8eb71864-0606-4291-a3eb-0034dd7041a1"), "홍길동", "NORMAL");
insert into customers values(UUID_TO_BIN("a34f531d-1323-4a5e-9042-7d25f00e84a3"), "세종대왕", "BLACK");


create TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) REFERENCES customers(customer_id),
    voucher_type varchar(30) NOT NULL,
    discount_value BIGINT NOT NULL
);

insert into vouchers(voucher_id, voucher_type, discount_value) values(UUID_TO_BIN("ca5672e8-1b3b-4a60-91bc-204938af4183"), "FIXED", 1000);
insert into vouchers(voucher_id, voucher_type, discount_value) values(UUID_TO_BIN("b1badc1d-be2a-454a-b85b-f01e44ef8ad6"), "PERCENT", 10);