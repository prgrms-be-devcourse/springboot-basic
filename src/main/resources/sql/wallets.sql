CREATE TABLE wallets
(
    wallet_id   varchar(20) PRIMARY KEY,
    customer_id varchar(20),
    voucher_id  varchar(20),
    deleted     boolean DEFAULT FALSE
);
INSERT INTO wallets(wallet_id, customer_id, voucher_id)
VALUES (1, 100, 1);
INSERT INTO wallets(wallet_id, customer_id, voucher_id)
VALUES (23, 101, 23);
INSERT INTO wallets(wallet_id, customer_id, voucher_id)
VALUES (3, 100, 46208228);
