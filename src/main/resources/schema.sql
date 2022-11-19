CREATE TABLE IF NOT EXISTS customers
(
    customer_id		int(5)      PRIMARY KEY 	AUTO_INCREMENT,
    customer_name	varchar(25) NOT NULL,
    email			varchar(40) NOT NULL        UNIQUE
);

CREATE TABLE IF NOT EXISTS voucher_type
(
    voucher_type_id		int(5)	    PRIMARY KEY 	AUTO_INCREMENT,
    voucher_name		varchar(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id 	BINARY(16)	    PRIMARY KEY,
    discount_value	long 		NOT NULL,
    voucher_type    int(5)	    NOT NULL,
    customer_id		int(5),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (voucher_type) REFERENCES voucher_type(voucher_type_id)
);

INSERT INTO voucher_type VALUES('1', 'FixedAmountVoucher');
INSERT INTO voucher_type VALUES('2', 'PercentDiscountVoucher');