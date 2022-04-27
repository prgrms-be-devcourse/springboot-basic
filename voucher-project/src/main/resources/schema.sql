

CREATE TABLE user (
                       id           BINARY(16)      PRIMARY KEY,
                       type         varchar(20)     NOT NULL       DEFAULT 'NORMAL',
                       name         varchar(20)     NOT NULL,
                       created_at   datetime(6)     NOT NULL       DEFAULT CURRENT_TIMESTAMP(6),
                       updated_at   datetime(6)     DEFAULT NULL
);


CREATE TABLE voucher (
                          id	        BINARY(16)      PRIMARY KEY,
                          type          varchar(20)     NOT NULL,
                          amount        bigint          NOT NULL	      DEFAULT 0,      -- 제약조건 : 음수불가, PERCENT일땐 0~100, 할인시 -금액 안됨
                          created_at    datetime(6)     NOT NULL          DEFAULT CURRENT_TIMESTAMP(6),
                          updated_at    datetime(6)     DEFAULT NULL
);

CREATE TABLE wallet (
                         id	        BINARY(16)      PRIMARY KEY,
                         user_id       BINARY(16)      NOT NULL,
                         voucher_id    BINARY(16)      NOT NULL,
                         created_at    datetime(6)     NOT NULL          DEFAULT CURRENT_TIMESTAMP(6),
                         updated_at    datetime(6)     DEFAULT NULL
);
