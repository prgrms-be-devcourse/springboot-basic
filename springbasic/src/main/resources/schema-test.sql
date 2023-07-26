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

INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN('c625fb58-2401-4071-b614-96136c4d9a91'), 'choi', 'choi@gmail.com');
INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN('daff49f1-61e9-40a8-9783-d4d1a77262b7'), 'june', 'june@gmail.com');
INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN('e311af99-adde-42fb-8d1c-2bcdcad83910'), 'hyuk', 'hyuk@gmail.com');