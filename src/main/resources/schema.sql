CREATE TABLE IF NOT EXISTS vouchers (
    voucher_id BINARY(16) PRIMARY KEY,
    discount_value BIGINT NOT NULL,
    voucher_type ENUM('fixed amount voucher', 'percent discount voucher') NOT NULL
);

CREATE TABLE IF NOT EXISTS customers (
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    is_black_customer tinyint(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS wallets (
  wallet_id BINARY(16) PRIMARY KEY,
  voucher_id BINARY(16) NOT NULL,
  customer_id BINARY(16) NOT NULL,
  FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id),
  FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);