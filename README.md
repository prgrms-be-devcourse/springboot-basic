# w3-SpringBoot_Part_A

## 🚀 4주차 미션

---

### 📌 미션 요구사항

(기본 과제) **바우처 관리 애플리케이션**

- 바우처 관리 애플리케이션에 단위테스트를 작성해보세요.
    - 가능한 많은 단위 테스트코드를 작성하려고 노력해보세요.
    - 엣지 케이스(예외 케이스)를 고려해서 작성해주세요.
    - Hamcrest 의 메쳐들을 다양하게 작성해보고 익숙해져 보세요.
- 바우처 관리 애플리케이션에서도 과정에서 다루었던 고객을 적용해보세요.
    - customers 테이블 정의 및 추가
    - CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현
- (1주차를 파일로 관리하게 했다.) 바우처 정보를 DB로 관리해보세요.
    - 바우처에 엔터티에 해당하는 vouchers 테이블을 한번 정의해보세요.
    - 바우처 레포지토리를 만들어보세요. (JdbcTemplate을 사용해서 구현)
    - 기존의 파일에서 바우처를 관리한 것을 vouchers 테이블을 통해서 CRUD가 되게 해보세요.

(심화 과제) **바우처 지갑을 만들어보세요.**

- 특정 고객에게 바우처를 할당할 수 있습니다.
- 고객이 어떤 바우처를 보유하고 있는지 조회할 수 있어야 합니다.
- 고객이 보유한 바우처를 제거할 수 있어야 합니다.
- 특정 바우처를 보유한 고객을 조회할 수 있어야 합니다.

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

---
## API Guide


### 고객 조회 요청

GET 요청을 사용하여 고객을 조회할 수 있다.

### Http Request
```http request
GET /kdt/api/v1/customers/4fdf585c-f7f8-4a7d-bc7e-492c6aaf932b HTTP/1.1
Content-Type: application/json 
Accept: application/json
Host: localhost:8080
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
   "vouchers":[
      {
         "voucherId":"0ef475ab-3761-4b51-b05a-1cf0aaa05abd",
         "name":"test voucher",
         "discount":100,
         "voucherType":"FIX",
         "createdAt":"2021-09-08T00:37:50.438875"
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
Content-Type: application/json 
Accept: application/json
Host: localhost:8080
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

Content-Type: application/json
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
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080

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
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080

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
