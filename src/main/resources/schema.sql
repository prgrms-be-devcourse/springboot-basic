CREATE TABLE customers
(
  customer_id   BINARY(16) PRIMARY KEY,
  name          VARCHAR(20)             NOT NULL,
  email         VARCHAR(50)             NOT NULL,
  last_login_at DATETIME(6)                      DEFAULT NULL,
  created_at    DATETIME(6)             NOT NULL DEFAULT NOW(6),
  customer_type ENUM ('WHITE', 'BLACK') NOT NULL DEFAULT 'WHITE',
  CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
  voucher_id   BINARY(16) PRIMARY KEY,
  name         VARCHAR(50) NOT NULL,
  value        BIGINT      NOT NULL DEFAULT 0,
  created_at   DATETIME(6) NOT NULL DEFAULT NOW(6),
  voucher_type VARCHAR(50) NOT NULL
);

CREATE TABLE customer_voucher
(
    customer_voucher_id BINARY(16) PRIMARY KEY,
    voucher_id          BINARY(16)  NOT NULL,
    customer_id         BINARY(16)  NOT NULL,
    used                BIT(1)      NOT NULL DEFAULT 0,
    used_at             DATETIME(6),
    created_at          DATETIME(6) NOT NULL DEFAULT NOW(6),
    expired_at          DATETIME(6),
    CONSTRAINT fk_voucher_id
        FOREIGN KEY (voucher_id)
            REFERENCES vouchers (voucher_id),
    CONSTRAINT fk_customer_id
        FOREIGN KEY (customer_id)
            REFERENCES customers (customer_id)
);

