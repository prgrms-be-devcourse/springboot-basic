CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    amount     INT(4) NOT NULL,
    type       ENUM ('FIXED', 'PERCENT') NOT NULL
);

INSERT INTO vouchers(voucher_id, amount, type)
VALUES (UUID_TO_BIN('da92052e-170a-11ec-9621-0242ac130002'), 10, 'FIXED');
INSERT INTO vouchers(voucher_id, amount, type)
VALUES (UUID_TO_BIN('da920768-170a-11ec-9621-0242ac130002'), 20, 'PERCENT');
INSERT INTO vouchers(voucher_id, amount, type)
VALUES (UUID_TO_BIN('da920862-170a-11ec-9621-0242ac130002'), 30, 'FIXED');
