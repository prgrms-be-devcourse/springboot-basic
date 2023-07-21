CREATE TABLE wallets
(
    wallet_id   BIGINT PRIMARY KEY,
    customer_id BIGINT,
    voucher_id  BIGINT,
    deleted     boolean DEFAULT FALSE
);
INSERT INTO wallets(wallet_id, customer_id, voucher_id)
VALUES (1, 100, 1);
INSERT INTO wallets(wallet_id, customer_id, voucher_id)
VALUES (23, 101, 23);
INSERT INTO wallets(wallet_id, customer_id, voucher_id)
VALUES (3, 100, 46208228);
