DROP TABLE IF EXISTS vouchers;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers
(
    customer_id VARCHAR(50) PRIMARY KEY,
    name        VARCHAR(20) NOT NULL,
    email       VARCHAR(50) NOT NULL,
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email   UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id  VARCHAR(50) PRIMARY KEY,
    policy      VARCHAR(50) NOT NULL,
    amount      INT NOT NULL,
    customer_id VARCHAR(50) UNIQUE,

    FOREIGN KEY (customer_id)
        references customers(customer_id)
            on delete cascade
            on update cascade
);