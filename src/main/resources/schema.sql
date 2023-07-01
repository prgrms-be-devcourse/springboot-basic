DROP TABLE IF EXISTS voucher_type;
DROP TABLE IF EXISTS voucher;

CREATE TABLE voucher_type
(
    id   INTEGER PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    UNIQUE KEY unq_type (type)
);

CREATE TABLE voucher
(
    id         BINARY(16) PRIMARY KEY,
    type_id    INTEGER NOT NULL,
    amount     LONG    NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (type_id) references voucher_type (id)
);

INSERT INTO voucher_type (id, type)
VALUES (1, 'FIXED_AMOUNT'),
       (2, 'PERCENT_AMOUNT');

