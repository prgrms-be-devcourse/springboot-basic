CREATE TABLE IF NOT EXISTS customers (
      customer_id BINARY(16) PRIMARY KEY,
      customer_name VARCHAR(255) NOT NULL,
      customer_type VARCHAR(255) NOT NULL

);

CREATE TABLE IF NOT EXISTS vouchers (
      voucher_id BINARY(16) PRIMARY KEY,
      customer_id BINARY(16),
      discount BIGINT NOT NULL,
      voucher_type ENUM('FIXED', 'PERCENT') NOT NULL,
      use_status_type VARCHAR(255) NOT NULL,
      FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);