DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS vouchers;

CREATE TABLE vouchers (
                          id CHAR(36) NOT NULL,
                          voucher_type VARCHAR(255) NOT NULL,
                          discount_amount INT NOT NULL,
                          PRIMARY KEY (id)
);

CREATE TABLE users (
                       id CHAR(36) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       PRIMARY KEY (id)
);
