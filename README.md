# SpringBoot Basic Weekly Mission 3
### 과제 요구사항

**(기본) 바우처 서비스 관리페이지 개발하기**

- Spring MVC 적용
- thymeleaf를 이용한 관리 페이지
- 관리 페이지 제공 기능 
  - [x]  조회페이지
  - [x]  상세페이지
  - [x]  입력페이지
  - [x]  삭제페이지

**(기본) 바우처 서비스의 API 개발하기**

- Spring MVC 적용
- JSON을 지원하는 REST API 개발
- API 제공 기능
  - [x]  전체 조회기능
  - [x]  특정 할인타입별 조회기능
  - [x]  바우처 추가기능
  - [x]  바우처 삭제기능
  - [x]  바우처 아이디로 조회 기능
  
### **바우처 관리 웹어플리케이션 설명**
- 조회 페이지에서는 생성된 모든 바우처 목록을 확인할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/b11f8548-da3b-4999-b18e-ce081c87927a/image.png)
- 상단의 검색 기능을 통해 바우처 아이디에 해당하는 바우처 정보를 확인할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/42528e4a-8fad-4878-9d00-4390991d9cf6/image.png)
- 입력 페이지에서는 fixed voucher나 percent voucher를 생성할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/de3937e9-85c9-4c72-a391-1052be861eda/image.png)
- 삭제 페이지에서는 생성한 바우처를 삭제할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/673d03db-fe85-49d5-9eec-c485735b648f/image.png)

### **바우처 관리 Rest API 설명**
- 전체 조회 기능

  resquest
  ```
  GET http://localhost:8080/demo_main_war_exploded2/api/v1/vouchers
  ```
  response
  ```
  [
  {
    "id": "197c3618-44c9-4d2d-8976-dbae7b75881b",
    "type": "percent",
    "amount": 100
  },
  {
    "id": "2484706b-f861-4a92-92b1-6f2a2abe5043",
    "type": "fixed",
    "amount": 5000
  },
  {
    "id": "2694aa41-c19e-4147-9da0-e1b785b110e3",
    "type": "fixed",
    "amount": 1000
  },
  {
    "id": "4cf0378c-2fa5-432d-9730-ae7755d4cf63",
    "type": "fixed",
    "amount": 1000
  }
  ]
  ```

- 특정 할인타입 별 조회 기능(fixed, percent 중 하나)

  resquest
  ```
  GET http://localhost:8080/demo_main_war_exploded2/api/v1/vouchers/fixed
  ```
  response
  ```
  [
  {
    "id": "2484706b-f861-4a92-92b1-6f2a2abe5043",
    "type": "fixed",
    "amount": 5000
  },
  {
    "id": "2694aa41-c19e-4147-9da0-e1b785b110e3",
    "type": "fixed",
    "amount": 1000
  },
  {
    "id": "4cf0378c-2fa5-432d-9730-ae7755d4cf63",
    "type": "fixed",
    "amount": 1000
  }
  ]
  ```
  resquest
  ```
  GET http://localhost:8080/demo_main_war_exploded2/api/v1/vouchers/percent
  ```
  response
  ```
  [
  {
    "id": "197c3618-44c9-4d2d-8976-dbae7b75881b",
    "type": "percent",
    "amount": 100
  }
  ]
  ``` 

- 바우처 추가 기능

  resquest
  ```
  POST http://localhost:8080/demo_main_war_exploded2/api/v1/voucher/create
  Content-Type: application/x-www-form-urlencoded
  
  type=fixed&amount=1000
  ```
  response
  ```
  successively create voucher
  ```
- 바우처 삭제 기능

  resquest
  ```
  GET http://localhost:8080/demo_main_war_exploded2/api/v1/voucher/delete/4cf0378c-2fa5-432d-9730-ae7755d4cf63
  ```
  response
  ```
  successively delete voucher
  ```
- 바우처 아이디로 조회 기능

  resquest
  ```
  GET http://localhost:8080/demo_main_war_exploded2/api/v1/voucher/2694aa41-c19e-4147-9da0-e1b785b110e3
  ```
  response
  ```
  {
    "id": "2694aa41-c19e-4147-9da0-e1b785b110e3",
    "type": "fixed",
    "amount": 1000
  }
  ```

### Class Diagram

![Untitled-2022-03-29-1415](https://user-images.githubusercontent.com/37391733/163552243-7c7b42fb-3fb1-49b1-b612-9cce565bef8b.png)
