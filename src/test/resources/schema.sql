CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id varchar(255) PRIMARY KEY,
    voucher_type varchar(255) not null ,
    discount bigint not null
);

CREATE TABLE IF NOT EXISTS customers
(
    customer_id varchar(255) PRIMARY KEY,
    name varchar(255) not null
);
CREATE TABLE IF NOT EXISTS wallets
(
    customer_id varchar(255) not null,
    voucher_id varchar(255) not null,
    PRIMARY KEY(customer_id, voucher_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (voucher_id) REFERENCES vouchers(voucher_id) ON DELETE CASCADE ON UPDATE CASCADE
);