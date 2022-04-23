CREATE TABLE members
(
    member_id BINARY(16) PRIMARY KEY,
    name      varchar(20) NOT NULL
);

-- 테스트에서만 FOREIGN KEY 풀어주기
CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    type       varchar(20) NOT NULL,
    amount     int         NOT NULL,
    member_id  BINARY(16)
--     ,FOREIGN KEY (member_id)
--         REFERENCES members(member_id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE
);