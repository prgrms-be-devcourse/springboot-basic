DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS vouchers;

CREATE TABLE users
(
    id   VARCHAR(50) PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE vouchers
(
    id         VARCHAR(50) PRIMARY KEY,
    discount   INT         NOT NULL,
    expired_at TIMESTAMP   NOT NULL,
    type       VARCHAR(20) NOT NULL,
    status     VARCHAR(20) NOT NULL
);
