CREATE TABLE IF NOT EXISTS customer
(
    id            BINARY(16)   NOT NULL,
    name          VARCHAR(255) NOT NULL,
    is_blacklisted TINYINT(1)   NOT NULL,
    PRIMARY KEY (id)
);