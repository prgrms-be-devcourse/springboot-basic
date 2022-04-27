

drop table if exists customer_has_vouchers;
drop table if exists customers;
drop table if exists vouchers;

CREATE TABLE customers
(
    customer_id    BINARY(16) PRIMARY KEY,
    name           varchar(20) NOT NULL,
    created_at     datetime(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE vouchers
(
    voucher_id	BINARY(16) PRIMARY KEY,
    type		TINYTEXT NOT NULL,
    discount_info	int	NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE customer_has_vouchers
(
    id	BIGINT	auto_increment	PRIMARY KEY,
    customer_id BINARY(16),
    voucher_id BINARY(16),
    FOREIGN KEY(customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY(voucher_id) REFERENCES vouchers(voucher_id)
);