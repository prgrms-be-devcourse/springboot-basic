# SpringBoot Basic Weekly Mission 3
### 과제 요구사항

**(기본) 바우처 서비스 관리페이지 개발하기**

- Spring MVC 적용
- thymeleaf를 이용한 관리 페이지
- 웹페이지 제공 기능 
  - [x]  조회페이지
  - [x]  상세페이지
  - [x]  입력페이지
  - [x]  삭제페이지

**(보너스) 바우처 지갑용 관리 페이지**
- 웹페이지 제공 기능
    - [x]  고객 보유 바우처 조회페이지(바우처 회수 가능)
    - [x]  특정 바우처 보유 고객정보 조회페이지
    - [x]  고객 바우처 부여 페이지
  
**(기본) 바우처 서비스의 API 개발하기**

- Spring MVC 적용
- JSON을 지원하는 REST API 개발
- API 제공 기능
  - [x]  바우처 조회 페이징
  - [x]  특정 할인 타입별 조회기능
  - [x]  바우처 아이디로 조회 기능
  - [x]  바우처 추가기능
  - [x]  바우처 삭제기능
  
### **바우처 관리 웹어플리케이션 설명**
- 조회 페이지에서는 생성된 모든 바우처 목록을 확인할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/0cd89797-04c0-44d3-abb8-93e34817a856/image.png)
- 바우처 아이디를 클릭하면 해당 바우처를 보유한 고객 정보를 확인할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/4a4ba09a-2911-41aa-9a68-955a34929ded/image.png)
- 고객 아이디를 클릭하면 해당 고객이 보유한 바우처 정보를 확인하고 바우처를 회수할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/9227b07d-8935-47de-87ca-4372984d7fe5/image.png)
- 상단의 검색 기능을 통해 바우처 아이디에 해당하는 바우처 정보를 확인할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/086eb6d3-9343-4e40-95c7-cd7f86596179/image.png)
- 입력 페이지에서는 fixed voucher나 percent voucher를 생성할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/64c5a75c-2dc1-4e37-8ed2-b75e27d5d40a/image.png)
- 삭제 페이지에서는 생성한 바우처를 삭제할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/55a4389f-f31b-475d-8f8e-ff02a6b108ef/image.png)
- 바우처 부여 페이지에서는 고객에게 바우처를 부여할 수 있습니다.
  ![](https://velog.velcdn.com/images/y005/post/370a23c9-5a1a-4ba1-9fd8-b75c6df369c5/image.png)

### **바우처 관리 Rest API 설명**
#### 1. 조회 기능
- param description

  |name| type   | value                                 |
    |--------|---------------------------------------|--------------|
    | type | string | fixed or percent. default is all type |
    | page | int    | default is 0                          |
    | size | int    | default is 10                         |

- request
  ```
  GET http://localhost:8080/demo_main_war_exploded2/api/v1/vouchers
  ```
- response
  ```
  [
  {
    "id": "75",
    "type": "percent",
    "amount": 100
  },
  {
    "id": "76",
    "type": "fixed",
    "amount": 5000
  },
  {
    "id": "77",
    "type": "fixed",
    "amount": 1000
  },
  {
    "id": "78",
    "type": "fixed",
    "amount": 1000
  }
  ]
  ```

- resquest
  ```
  GET http://localhost:8080/demo_main_war_exploded2/api/v1/vouchers?type=fixed
  ```
- response
  ```
  [
  {
    "id": "76",
    "type": "fixed",
    "amount": 5000
  },
  {
    "id": "77",
    "type": "fixed",
    "amount": 1000
  },
  {
    "id": "78",
    "type": "fixed",
    "amount": 1000
  }
  ]
  ```
 - request
  ```
  GET http://localhost:8080/demo_main_war_exploded2/api/v1/vouchers?type=percent
  ```
- response
  ```
  [
  {
    "id": "75",
    "type": "percent",
    "amount": 100
  }
  ]
  ``` 

#### 2. 바우처 추가 기능

  - request
    ```
    POST http://localhost:8080/demo_main_war_exploded2/api/v1/vouchers/create
    Content-Type: application/json
    
    {
      "type" : "fixed",
      "amount" : 1000 
    }
    ```

- response
  ```
  successively create voucher
  ```
  
#### 3. 바우처 삭제 기능

  - resquest
    ```
    GET http://localhost:8080/demo_main_war_exploded2/api/v1/vouchers/delete/75
    ```
  - response
    ```
    successively delete voucher
    ```
    
#### 4. 바우처 아이디 검색 기능

  - request
    ```
    GET http://localhost:8080/demo_main_war_exploded2/api/v1/vouchers/77

    ```
  - response
    ```
    {
      "id": "77",
      "type": "fixed",
      "amount": 1000
    }
    ```