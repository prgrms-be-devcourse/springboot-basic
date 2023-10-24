CREATE TABLE  IF NOT EXISTS vouchers
(
    id     CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    type   VARCHAR(255) NOT NULL,
    amount BIGINT       NOT NULL
    );

CREATE TABLE  IF NOT EXISTS customers
(
    id          CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name        VARCHAR(255) NOT NULL,
    blacklisted BOOLEAN      NOT NULL
    );
