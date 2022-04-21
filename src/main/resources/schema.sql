DROP TABLE IF EXISTS voucher;

-- 바우처 데이터이다.
CREATE TABLE voucher
(
    voucher_id      UUID                        NOT NULL , -- 바우처 PK
    voucher_type    ENUM('fixed' , 'percent')   NOT NULL , -- 바우처 타입
    amount          NUMERIC(20)                 NOT NULL , -- 정액 할인 바우처 -> 가격
    percent         TINYINT                     NOT NULL , -- 정률 할인 바우처 -> 퍼센트
    PRIMARY KEY (voucher_id)
)