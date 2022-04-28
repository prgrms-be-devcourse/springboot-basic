DROP TABLE IF EXISTS voucher;

DROP TABLE IF EXISTS customer;

-- 고객 데이터이다.
CREATE TABLE customer
(
    customer_id   UUID        NOT NULL, -- 고객 PK
    customer_name VARCHAR(20) NOT NULL, -- 고객 이름
    blacklist     BOOLEAN     NOT NULL, -- 블랙리스트 여부
    PRIMARY KEY (customer_id)
);

-- 바우처 데이터이다.
CREATE TABLE voucher
(
    voucher_id   UUID                       NOT NULL, -- 바우처 PK
    customer_id  UUID        DEFAULT NULL,            -- 보유 고객 PK
    voucher_type ENUM ('fixed' , 'percent') NOT NULL, -- 바우처 타입
    amount       NUMERIC(20) DEFAULT NULL,            -- 정액 할인 바우처 -> 가격
    percent      TINYINT     DEFAULT NULL,            -- 정률 할인 바우처 -> 퍼센트
    PRIMARY KEY (voucher_id),
    CONSTRAINT fk_voucher_to_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);