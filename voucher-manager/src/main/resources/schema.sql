CREATE TABLE voucher
(
    voucher_id     VARCHAR(36) PRIMARY KEY,
    voucher_type   VARCHAR(50)    NOT NULL,
    discount_value DECIMAL(10, 2) NOT NULL
);

CREATE TABLE customer
(
    email_address VARCHAR(100) PRIMARY KEY,
    created_date  TIMESTAMP NOT NULL
);

CREATE TABLE customerVoucherWallet
(
    customer_email_address VARCHAR(100) NOT NULL,
    voucher_id             VARCHAR(36)  NOT NULL,

    FOREIGN KEY (customer_email_address) REFERENCES customer (email_address) ON DELETE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES voucher (voucher_id) ON DELETE CASCADE,
    PRIMARY KEY (customer_email_address, voucher_id)
);
