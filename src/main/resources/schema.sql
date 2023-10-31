CREATE TABLE IF NOT EXISTS customers (
      customer_id BINARY(16) PRIMARY KEY,
      customer_name VARCHAR(255) NOT NULL,
      customer_type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS vouchers (
      voucher_id BINARY(16) PRIMARY KEY,
      discount BIGINT NOT NULL,
      voucher_type ENUM('FIXED', 'PERCENT') NOT NULL,
      use_status_type VARCHAR(255) NOT NULL,
      created_at DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS wallets (
      wallet_id   INT PRIMARY KEY AUTO_INCREMENT,
      customer_id BINARY(16) NOT NULL,
      voucher_id  BINARY(16)  NOT NULL,
      deleted CHAR(1) DEFAULT 'N'
);
