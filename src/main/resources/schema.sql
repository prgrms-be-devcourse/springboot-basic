DROP TABLE IF EXISTS vouchers CASCADE;
DROP TABLE IF EXISTS customers CASCADE;

-- 바우처 데이터 입니다.
create table vouchers
(
    voucher_id   BINARY(16) PRIMARY KEY, -- 바우처 PK
    value        DECIMAL     NOT NULL,   -- 바우처 값
    voucher_type VARCHAR(50) NOT NULL    -- 바우처 타입
);

-- 고객 데이터 입니다.
create table customers
(
    customer_id   BINARY(16) PRIMARY KEY, -- 고객 PK
    name          VARCHAR(50) NOT NULL,   -- 고객 이름
    customer_type VARCHAR(50) NOT NULL    -- 고객 유형
);