DROP TABLE IF EXISTS voucher;

CREATE TABLE voucher (
    id   BINARY(16)  NOT NULL PRIMARY KEY,
    type VARCHAR(16) NOT NULL,
    data INT         NOT NULL
)
