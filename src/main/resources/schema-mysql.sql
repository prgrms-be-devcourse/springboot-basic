CREATE TABLE IF NOT EXISTS customers (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    customerStatus VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS vouchers (
    id VARCHAR(36) PRIMARY KEY,
    type VARCHAR(20),
    discount INT,
    creationDateTime TIMESTAMP
);

CREATE TABLE IF NOT EXISTS VoucherWallet (
    customerId VARCHAR(36),
    voucherId VARCHAR(36),
    FOREIGN KEY (customerId) REFERENCES customers(id),
    FOREIGN KEY (voucherId) REFERENCES vouchers(id),
    PRIMARY KEY (customerId, voucherId)
);
