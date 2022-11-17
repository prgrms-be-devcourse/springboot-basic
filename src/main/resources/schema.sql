CREATE DATABASE voucher;

USE voucher;

DROP TABLE vouchers;
DROP TABLE voucher_type;
DROP TABLE customers;

CREATE TABLE customers
(
    customer_id		int(5)      PRIMARY KEY 	AUTO_INCREMENT,
    customer_name	varchar(25) NOT NULL,
    email			varchar(40) NOT NULL UNIQUE
);

CREATE TABLE voucher_type
(
    voucher_type_id		int(5)	    PRIMARY KEY 	AUTO_INCREMENT,
    voucher_name		varchar(30) NOT NULL
);

CREATE TABLE vouchers
(
    voucher_id		int(5)	    PRIMARY KEY		AUTO_INCREMENT,
    voucher_number 	BINARY(16)	NOT NULL,
    discount_value	long 		NOT NULL,
    voucher_type    int(5)	    NOT NULL,
    customer_id		int(5),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (voucher_type) REFERENCES voucher_type(voucher_type_id)
);

INSERT INTO voucher_type VALUES('1', 'FixedAmountVoucher');
INSERT INTO voucher_type VALUES('2', 'PercentDiscountVoucher');

SELECT * FROM customers;
SELECT * FROM voucher_type;
SELECT * FROM vouchers;