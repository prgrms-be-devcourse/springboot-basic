CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          VARCHAR(20) NOT NULL,
    email         VARCHAR(50) NOT NULL,
    last_login_at DATETIME(6) DEFAULT NULL,
    created_at    DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    amount     INT(4) NOT NULL,
    type       ENUM ('FIXED', 'PERCENT') NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP (6)
);

CREATE TABLE wallets
(
    wallet_id   BINARY(16) PRIMARY KEY,
    customer_id BINARY(16),
    voucher_id  BINARY(16)
);


INSERT INTO customers(customer_id, name, email)
VALUES (UUID_TO_BIN('75810d66-19bb-11ec-9621-0242ac130002'), 'Sam', 'sam@gmail.com');
INSERT INTO customers(customer_id, name, email)
VALUES (UUID_TO_BIN('75810f82-19bb-11ec-9621-0242ac130002'), 'Bumkey', 'bumkey@gmail.com');
INSERT INTO customers(customer_id, name, email)
VALUES (UUID_TO_BIN('75811068-19bb-11ec-9621-0242ac130002'), 'Buri', 'buri@gmail.com');
INSERT INTO customers(customer_id, name, email)
VALUES (UUID_TO_BIN('46b74b34-19bc-11ec-9621-0242ac130002'), 'Marco', 'marco@gmail.com');


INSERT INTO vouchers(voucher_id, amount, type)
VALUES (UUID_TO_BIN('9cbb3d0a-158a-11ec-82a8-0242ac130003'), 10, 'FIXED');
INSERT INTO vouchers(voucher_id, amount, type)
VALUES (UUID_TO_BIN('9cbb3f80-158a-11ec-82a8-0242ac130003'), 20, 'PERCENT');
INSERT INTO vouchers(voucher_id, amount, type)
VALUES (UUID_TO_BIN('9cbb4228-158a-11ec-82a8-0242ac130003'), 30, 'FIXED');

INSERT INTO wallets(wallet_id, customer_id, voucher_id)
VALUES (UUID_TO_BIN('1389c2a0-19bc-11ec-9621-0242ac130002'), UUID_TO_BIN('75810d66-19bb-11ec-9621-0242ac130002'),
        UUID_TO_BIN('9cbb3d0a-158a-11ec-82a8-0242ac130003'));
INSERT INTO wallets(wallet_id, customer_id, voucher_id)
VALUES (UUID_TO_BIN('1389c4bc-19bc-11ec-9621-0242ac130002'), UUID_TO_BIN('75810f82-19bb-11ec-9621-0242ac130002'),
        UUID_TO_BIN('9cbb3f80-158a-11ec-82a8-0242ac130003'));
INSERT INTO wallets(wallet_id, customer_id, voucher_id)
VALUES (UUID_TO_BIN('1389c5a2-19bc-11ec-9621-0242ac130002'), UUID_TO_BIN('75811068-19bb-11ec-9621-0242ac130002'),
        UUID_TO_BIN('9cbb3f80-158a-11ec-82a8-0242ac130003'));
