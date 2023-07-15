CREATE TABLE vouchers
(
    voucher_id  VARCHAR(50) PRIMARY KEY,
    policy      VARCHAR(50) NOT NULL,
    amount      INT NOT NULL,
    customer_id VARCHAR(50),

    FOREIGN KEY (customer_id)
        references customers(customer_id)
        on delete cascade
        on update cascade
);
