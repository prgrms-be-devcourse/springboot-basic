# 미션 내용


## SpringBoot Part3 Weekly Mission(~11/25)

**(기본) 바우처 서비스 관리페이지 개발하기**

    - Spring MVC를 적용해서 thymeleaf 템플릿을 설정해보세요.
    - 커맨드로 지원했던 기능을 thymeleaf를 이용해서 관리페이지를 만들고 다음 기능을 지원가능하게 해보세요
      - [x]  조회페이지
      - [x]  상세페이지
      - [x]  입력페이지
      - [x]  삭제기능

**(기본) 바우처 서비스의 API 개발하기**

    - Spring MVC를 적용해서 JSON과 XML을 지원하는 REST API를 개발해보세요
      - [ ]  전체 조회기능
      - [ ]  조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
      - [ ]  바우처 추가기능
      - [ ]  바우처 삭제기능
      - [ ]  바우처 아이디로 조회 기능

**(보너스) 바우처 지갑용 관리페이지를 만들어보세요.**

## SpringBoot Part3 Weekly Mission 기능 정리 🚀

### 🔥 타임리프를 이용한 고객, 바우처 관리 애플리케이션 구현 🔥

- [x] 조회 페이지
  - [x] 고객 검색
    - GET `/view/v1/customers/search`
  - [x] 바우처 검색 
    - GET `/view/v1/vouchers/search`
- [x] 상세 페이지
  - [x] 고객 리스트 페이지 
    - GET `/view/v1/customers`
  - [x] 바우처 리스트 페이지 
    - GET `/view/v1/vouchers`
  - [x] 고객 바우처 상세 페이지 
    - GET `/view/v1/customers/vouchers/{customerId}`
- [x] 입력 페이지
  - [x] 고객 등록 페이지 
    - GET, POST `/view/v1/customer/add`
  - [x] 바우처 등록 페이지 
    - GET, POST `/view/v1/vouchers/add`
  - [x] 고객 바우처(지갑) 등록 페이지 
    - GET, POST `/view/v1/customers/vouchers/add/{customerId}`
- [x] 삭제 페이지
  - [x] 고객 삭제 페이지
    - DELETE `/view/v1/customers/{customerId}`
  - [x] 바우처 삭제 페이지
    - `/view/v1/vouchers/{voucherId}`
  - [x] 고객 바우처(지갑) 삭제 페이지
    - DELETE `/view/v1/customers/vouchers/{customerId}/{voucherId}`

### 🔥 JSON을 이용한 고객, 바우처 관리 애플리케이션 구현 🔥

- [x] 전체 조회기능
  - [x] 고객 전체 조회
    - GET, `/api/v1/customers`
  - [x] 바우처 전체 조회
    - GET, `api/v1/vouchers`
  - [x] 고객, 바우처(지갑) 전체 조회
    - GET, `api/v1/customers/vouchers`
  - [x] 조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
    - [x] 조건 조회 : 바우처 생성 기간
    - [x] 조건 조회 : 바우처 할인 타입별
    - [x] 조건 조회 : 바우처 아이디
    - `api/v1/vouchers/search`
      ```
      // Query Params
      
      voucherId
      voucherType
      findStartAt
      findEndAt
      
      ---
      
      - 바우처 검색 조건 : 바우처 아이디
      /api/v1/vouchers/search?voucherId=1
      
      - 바우처 검색 조건 : 바우처 타입
      /api/v1/vouchers/search?voucherType=fixed
      
      - 바우처 검색 조건 : 바우처 생성기간
      /api/v1/vouchers/search?findEndAt=2022-12-31T01:30&findStartAt=2022-11-01T01:30      
      
      - 바우처 검색 조건 : 바우처 아이디, 바우처 생성기간
      /api/v1/vouchers/search?findEndAt=2022-12-31T01:30&findStartAt=2022-11-01T01:30&voucherId=1

      - 바우처 검색 조건 : 바우처 타입, 바우처 생성기간
      /api/v1/vouchers/search?voucherType=fixed&findStartAt=2022-11-01T01:30&voucherId=1
      
      - 바우처 검색 조건 : 바우처 아이디, 바우처 타입
      /api/v1/vouchers/search?voucherId=1&voucherType=fixed
      
      - 바우처 검색 조건 : 바우처 아이디, 바우처 타입, 바우처 생성 기간 
      /api/v1/vouchers/search?voucherId=1&voucherType=fixed&findEndAt=2022-12-31T01:30&findStartAt=2022-11-01T01:30
      ```
- [x] 바우처 추가기능
  - POST, `/api/v1/vouchers`
    ```json
    {
      "discountValue": 100,
      "voucherType": "fixed",
      "startAt": "2022-11-30T00:00",
      "endAt": "2022-12-31T00:00"
    }
    ```
- [x] 바우처 삭제기능
  - DELETE, `/api/v1/vouchers`
      ```
      // Query Params
      
      voucherId
      
      ---
    
      - 바우처 삭제 조건 : 바우처 아이디 
      /api/v1/vouchers?voucherId=1