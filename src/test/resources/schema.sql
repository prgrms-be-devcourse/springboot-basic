CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id varchar(255) PRIMARY KEY,
    voucher_type varchar(255),
    discount bigint
);

CREATE TABLE IF NOT EXISTS customers
(
    customer_id varchar(255) PRIMARY KEY,
    name varchar(255)
);
create TABLE IF NOT EXISTS wallets
(
    wallet_id bigint auto_increment,
    customer_id varchar(255),
    voucher_id varchar(255),
    PRIMARY KEY(customer_id, voucher_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers(voucher_id)
);