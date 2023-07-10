CREATE TABLE vouchers
(
    id VARCHAR(36) PRIMARY KEY,
    type VARCHAR(255),
    discount BIGINT,
    creationDateTime TIMESTAMP
);

CREATE TABLE customers
(
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    customerStatus VARCHAR(20)
);

CREATE TABLE VoucherWallet
(
    customerId VARCHAR(36),
    voucherId VARCHAR(36),
    PRIMARY KEY (customerId, voucherId),
    FOREIGN KEY (customerId) REFERENCES customers(id),
    FOREIGN KEY (voucherId) REFERENCES vouchers(id)
);
