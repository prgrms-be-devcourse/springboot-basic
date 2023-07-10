CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL
);

CREATE TABLE vouchers
(
  voucher_code      BINARY(16) PRIMARY KEY,
  value             DOUBLE PRECISION NOT NULL,
  type              varchar(20) NOT NULL,
  expiration_date   DATE,
  is_active         BIT,
  customer_id   BINARY(16),
  FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

ALTER TABLE vouchers
ADD CONSTRAINT fk_vouchers_customers
FOREIGN KEY (customer_id)
REFERENCES customers(customer_id)
ON DELETE CASCADE;