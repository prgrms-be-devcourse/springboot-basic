CREATE TABLE members
(
    member_id BINARY(16) PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- 테스트에서만 FOREIGN KEY 풀어주기
CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    amount BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    member_id BINARY(16) NOT NULL,
--     FOREIGN KEY (member_id)
--         REFERENCES members(member_id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE,
    UNIQUE INDEX member_id_index(member_id)
);