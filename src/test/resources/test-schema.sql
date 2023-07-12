create table vouchers
(
    voucher_id     binary(16)  not null primary key,
    voucher_type   varchar(20) not null,
    discount_value double      not null,
    customer_id    binary(16)  not null
#     constraint fk_customer_customerId
#         foreign key (customer_id) references voucher_system.customers (customer_id)
);

CREATE TABLE customers
(
    customer_id BINARY(16)  not null PRIMARY KEY,
    name        varchar(20) NOT NULL
);

set @customer_id1 = UUID_TO_BIN(UUID());
set @customer_id2 = UUID_TO_BIN(UUID());
insert into customers(customer_id, name)
VALUES (@customer_id1, '사과');
insert into customers(customer_id, name)
VALUES (@customer_id2, '딸기');

insert into vouchers(voucher_id, voucher_type, discount_value, customer_id)
VALUES (UUID_TO_BIN(UUID()), 'FIXED_AMOUNT', 223.41, @customer_id1);
insert into vouchers(voucher_id, voucher_type, discount_value, customer_id)
VALUES (UUID_TO_BIN(UUID()), 'PERCENT_DISCOUNT', 23.41, @customer_id1);

insert into vouchers(voucher_id, voucher_type, discount_value, customer_id)
VALUES (UUID_TO_BIN(UUID()), 'FIXED_AMOUNT', 21, @customer_id2);
insert into vouchers(voucher_id, voucher_type, discount_value, customer_id)
VALUES (UUID_TO_BIN(UUID()), 'PERCENT_DISCOUNT', 87.4, @customer_id2);

