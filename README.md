# w3-SpringBoot_Part_A

## 🚀 5주차 미션

---

### 📌 미션 요구사항

### Requirements

**(기본) 바우처 서비스 관리페이지 개발하기**

- Spring MVC를 적용해서 thymeleaf 템플릿을 설정해보세요.
- 커맨드로 지원했던 기능을 thymeleaf를 이용해서 관리페이지를 만들고 다음 기능을 지원가능하게 해보세요
  - 조회페이지
  - 상세페이지
  - 입력페이지
  - 삭제기능

**(기본) 바우처 서비스의 API 개발하기**

- Spring MVC를 적용해서 JSON과 XML을 지원하는 REST API를 개발해보세요
  - 전체 조회기능
  - 조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
  - 바우처 추가기능
  - 바우처 삭제기능
  - 바우처 아이디로 조회 기능

**(보너스) 바우처 지갑용 관리페이지를 만들어보세요.**

---

## ⚙ 개발 환경

```
java 16, 
maven 3.8.2
springboot 2.5.3
```

## ▶ 실행 방법

```shell
KdtApplication 클래스의 main 메소드 실행

또는

해당 프로젝트 경로에서
1. mvn package
2. cd target
3. java -jar kdt-spring-demo-0.0.1-SNAPSHOT.jar 
```

## 📄 ERD
<img width="670" alt="스크린샷 2021-09-08 오전 12 32 36" src="https://user-images.githubusercontent.com/58363663/132372235-79a595ca-5210-4eb8-9044-c338017b2733.png">


```sql
CREATE TABLE customers
(
    customer_id    BINARY(16) PRIMARY KEY,
    name           varchar(20) NOT NULL,
    email          varchar(50) NOT NULL,
    last_login_at  datetime(6)             DEFAULT NULL,
    created_at     datetime(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id     BINARY(16) PRIMARY KEY,
    name           varchar(20) NOT NULL,
    voucher_type   ENUM('FIX', 'PERCENT') NOT NULL,
    discount       int(4) NOT NULL,
    created_at     datetime(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE wallets
(
    wallet_id BINARY(16) PRIMARY KEY,
    customer_id       BINARY(16),
    voucher_id        BINARY(16),
    created_at        datetime(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id)
);

```
---

## 바우처 관리 페이지
### 바우처 등록
![바우처등록](https://user-images.githubusercontent.com/58363663/132718281-af378cf5-cf94-49dc-851d-9ea95555b23c.gif)

### 바우처 조회
![바우처조회](https://user-images.githubusercontent.com/58363663/132718320-967dd284-4c8f-41c3-977e-8830a176f43c.gif)

### 지갑 추가
![지갑추가](https://user-images.githubusercontent.com/58363663/132718357-acf6eef9-b675-4e90-afa4-ad3daec546d9.gif)

### 바우처 상세 조회
![지갑조회](https://user-images.githubusercontent.com/58363663/132718349-9a99f131-8186-4ab2-a300-b12e2b980e4f.gif)

### 삭제
![삭제](https://user-images.githubusercontent.com/58363663/132718336-fd60fd34-28b1-41ae-a1b5-404e33ee22e1.gif)

### 임의 고객 추가
![임의고객추가](https://user-images.githubusercontent.com/58363663/132718348-3a8198d0-47e3-4e09-933a-6a8a64c30116.gif)


---
## API Guide


### 고객 조회 요청

GET 요청을 사용하여 고객을 조회할 수 있다.

### Http Request
```http request
GET /kdt/api/v1/customers/4fdf585c-f7f8-4a7d-bc7e-492c6aaf932b HTTP/1.1
```

### Http Response
```http request
HTTP/1.1 200 OK

Content-Type: application/json
        
{
   "customerId" : "4fdf585c-f7f8-4a7d-bc7e-492c6aaf932b",
   "name" : "tester",
   "email" : "tester@email.com",
   "createdAt" : "2021-09-08T00:37:50.433914",
   "lastLoginAt" : null,
   "customerType" : "NORMAL",
   "vouchers" : [
      {
         "voucherId" : "0ef475ab-3761-4b51-b05a-1cf0aaa05abd",
         "name" : "test voucher",
         "discount" : 100,
         "voucherType" : "FIX",
         "createdAt" : "2021-09-08T00:37:50.438875"
      }
   ]
}
```

### Http Response (실패)

```http request
HTTP/1.1 404 NOT FOUND

Content-Type: application/json
{
   "errorCode" : "404 NOT_FOUND",
   "message" : "not found customerId : 9516bd07-721d-449f-80ec-f2075eb08aba"
}
```

<br>
<br>

### 바우처 조회 요청
GET 요청을 사용하여 바우처를 조회할 수 있다.

### Http Request

```http request
GET /kdt/api/v1/vouchers/4e62cb61-7dd8-421c-99e6-964e7fabca37 HTTP/1.1
```

### Http Response

```http request
HTTP/1.1 200 OK

Content-Type: application/json
        
{
   "voucherId" : "4e62cb61-7dd8-421c-99e6-964e7fabca37",
   "name" : "test voucher",
   "discount" : 100,
   "voucherType" : "FIX",
   "createdAt" : "2021-09-08T00:50:36.733667",
   "customers" : [
      {
         "customerId" : "ee30da45-6cf9-4443-8183-462b4e7217ca",
         "name" : "tester",
         "email" : "tester@email.com",
         "createdAt" : "2021-09-08T00:50:36.704613",
         "lastLoginAt" : null,
         "customerType" : "NORMAL"
      }
   ]
}
```

### Http Response (실패)

```http request
HTTP/1.1 404 Not Found
{
   "errorCode" : "404 NOT_FOUND",
   "message" : "not found voucher_id : a6662c4c-7fe4-463d-9d23-abf834624974"
}
```

<br>
<br>

### 지갑 생성

POST 요청을 통해 지갑을 생성할 수 있다.

### Http Request
```http request
POST /kdt/api/v1/customers/wallet HTTP/1.1

{
    "customerId" : "0e5da4e1-189d-4ad3-a4f5-03ca82abbd0e",
    "voucherId" : "9516bd07-721d-449f-80ec-f2075eb08aba"
}
```

### Http Response
```http request
HTTP/1.1 201 Created

Content-Type: application/json

{
    "customerId" : "0e5da4e1-189d-4ad3-a4f5-03ca82abbd0e",
    "voucherId" : "9516bd07-721d-449f-80ec-f2075eb08aba"
}
```


<br>
<br>


### 지갑 삭제

DELETE 요청을 통해 지갑을 삭제할 수 있다.

### Http Request
```http request
DELETE /kdt/api/v1/customers/wallet HTTP/1.1

{
    "customerId" : "0e5da4e1-189d-4ad3-a4f5-03ca82abbd0e",
    "voucherId" : "9516bd07-721d-449f-80ec-f2075eb08aba"
}
```

### Http Response
```http request
HTTP/1.1 200 OK

Content-Type: application/json

{
    "customerId" : "0e5da4e1-189d-4ad3-a4f5-03ca82abbd0e",
    "voucherId" : "9516bd07-721d-449f-80ec-f2075eb08aba"
}
```


<br>
<br>


### 바우처 타입별 조회

GET 요청을 통해 바우처 타입별로 조회할 수 있다.

### Http Request
```http request
GET /kdt/api/v1/vouchers/search/type?voucherType=FIX HTTP/1.1
```

### Http Response
```http request
HTTP/1.1 200 OK

Content-Type: application/json

[
  {
    "voucherId": "c7cb2751-fa32-43bf-baf5-146feaeb958a",
    "name": "피자 할인 쿠폰",
    "discount": "1000원",
    "voucherType": "FIX",
    "createdAt": "12/09/2021 23:55:40"
  },
  {
    "voucherId": "dfd43d50-7629-4fa2-b2bc-8d09b796b456",
    "name": "치킨 할인 쿠폰",
    "discount": "100원",
    "voucherType": "FIX",
    "createdAt": "12/09/2021 23:57:05"
  }
]
```

### 바우처 기간별 조회

GET 요청을 통해 바우처 생성 기간별로 조회할 수 있다.

### Http Request
```http request
GET /kdt/api/v1/vouchers/search/createAt?beforeDate=2020-02-02&afterDate=2022-02-02 HTTP/1.1
```

### Http Response
```http request
HTTP/1.1 200 OK

Content-Type: application/json

[
  {
    "voucherId": "ba091766-e94f-481f-bde9-8ef7eaef768d",
    "name": "전 상품 5프로 할인 쿠폰",
    "discount": "5%",
    "voucherType": "PERCENT",
    "createdAt": "12/09/2021 23:59:45"
  },
  {
    "voucherId": "c7cb2751-fa32-43bf-baf5-146feaeb958a",
    "name": "피자 할인 쿠폰",
    "discount": "1000원",
    "voucherType": "FIX",
    "createdAt": "12/09/2021 23:55:40"
  },
  {
    "voucherId": "dfd43d50-7629-4fa2-b2bc-8d09b796b456",
    "name": "치킨 할인 쿠폰",
    "discount": "100원",
    "voucherType": "FIX",
    "createdAt": "12/09/2021 23:57:05"
  }
]
```