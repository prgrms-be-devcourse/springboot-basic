DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS vouchers;

CREATE TABLE customers
(
    customer_id		int(5)      PRIMARY KEY 	AUTO_INCREMENT,
    customer_name	varchar(25) NOT NULL,
    email			varchar(40) NOT NULL        UNIQUE
);

CREATE TABLE vouchers
(
    voucher_id     BINARY(16) PRIMARY KEY,
    discount_value long        NOT NULL,
    voucher_name   varchar(50) NOT NULL,
    customer_id    int(5)
)