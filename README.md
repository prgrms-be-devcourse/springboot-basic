# SpringBoot Basic Weekly Mission

스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

## 요구 사항

**(기본) 바우처 서비스 관리페이지 개발하기**

- Spring MVC를 적용해서 thymeleaf 템플릿을 설정해보세요.
- 커맨드로 지원했던 기능을 thymeleaf를 이용해서 관리페이지를 만들고 다음 기능을 지원가능하게 해보세요
    - [x]  조회페이지
    - [x]  상세페이지
    - [x]  입력페이지
    - [x]  삭제기능

**(기본) 바우처 서비스의 API 개발하기**

- Spring MVC를 적용해서 JSON과 XML을 지원하는 REST API를 개발해보세요
    - [x]  전체 조회기능
    - [x]  조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
    - [x]  바우처 추가기능
    - [x]  바우처 삭제기능
    - [x]  바우처 아이디로 조회 기능

- [ ] **(보너스) 바우처 지갑용 관리페이지를 만들어보세요.**

## 프로젝트 실행 방법

1. terminal 에 아래 명령어를 입력해주세요. mysql docker image 를 다운 받고 컨테이너를 생성하는 과정입니다.
   ``` docker run --name voucher_container -e MYSQL_ROOT_PASSWORD=root1234! -p 3306:3306 -d mysql:8```

2. intellij 에서 아래 그림처럼 DB 환경변수를 등록해주세요.
   DB_URL:  ```jdbc:mysql://localhost:3306/voucher_management?characterEncoding=UTF-8```
   DB_USER: ```root```
   DB_PASSWORD: ```root1234!```

![제목 3](https://user-images.githubusercontent.com/82152173/206137066-29988188-7a65-4d9e-b836-35b2dc43e8b7.jpg)

![제목 5asdf](https://user-images.githubusercontent.com/82152173/206136966-bfbfe570-f17b-4f53-9525-913b22fc89d9.jpg)

Environment variables 를 눌러 환경 변수를 추가합니다.

![제목 5](https://user-images.githubusercontent.com/82152173/206137433-baf0632d-3206-47e0-821d-5ab7acd98a55.jpg)

3. 인텔리제이에서 아래 그림처럼 DB에 연결합니다.
   ![제목 없음](https://user-images.githubusercontent.com/82152173/206138485-43823d76-c413-4c60-891c-8428a1afc3fe.jpg)

![제목 없음2](https://user-images.githubusercontent.com/82152173/206138764-fc3af3be-3d4b-41b8-84c1-c14422e82b06.jpg)

4. console 창에 아래 sql 문을 실행하면 프로그램을 실행할 준비를 마쳤습니다.

```
create database voucher_management;
use voucher_management;

CREATE TABLE IF NOT EXISTS vouchers
(
    voucher_id   VARCHAR(36) PRIMARY KEY,
    voucher_type VARCHAR(10) NOT NULL,
    discount     LONG        NOT NULL,
    created_at   TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS customers
(
    customer_id      VARCHAR(36) PRIMARY KEY,
    customer_type    VARCHAR(10) NOT NULL,
    created_at       TIMESTAMP   NOT NULL,
    last_modified_at TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS wallets
(
    voucher_id  VARCHAR(36) NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    created_at  TIMESTAMP   NOT NULL,
    PRIMARY KEY (voucher_id, customer_id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE
);
```

## ERD

![voucher_w2_erd](https://user-images.githubusercontent.com/82152173/203057234-396bc221-7a8d-49e7-9fba-14d1a7044701.PNG)
