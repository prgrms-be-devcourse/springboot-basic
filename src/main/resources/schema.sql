create TABLE customers (
		customer_id UUID,
		customer_name VARCHAR(30),
		customer_type VARCHAR(30),
		CONSTRAINT customer_pk PRIMARY KEY(customer_id)
);

create TABLE vouchers (
		voucher_id UUID,
		discount_value BIGINT,
		voucher_type VARCHAR(30),
		customer_id UUID,
		CONSTRAINT voucher_pk PRIMARY KEY(voucher_id),
		CONSTRAINT voucher_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);