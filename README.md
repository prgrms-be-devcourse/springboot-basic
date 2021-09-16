## [스프링 3주차 과제 요구사항]
**(기본) 바우처 서비스 관리페이지 개발하기**

- Spring MVC를 적용해서 thymeleaf 템플릿을 설정해보세요.
- 커맨드로 지원했던 기능을 thymeleaf를 이용해서 관리페이지를 만들고 다음 기능을 지원가능하게 해보세요
    - [x] 조회페이지
    - [x] 상세페이지
    - [x] 입력페이지
    - [x] 삭제기능

**(기본) 바우처 서비스의 API 개발하기**

- Spring MVC를 적용해서 JSON과 XML을 지원하는 REST API를 개발해보세요
    - [x] 전체 조회기능
    - [x] 바우처 아이디로 조회 기능
    - [x] 바우처 추가기능
    - [x] 조건별 조회기능
        - [x] 바우처 생성기간
        - [x] 특정 할인타입별
    - [x] 바우처 삭제기능

**(보너스) 바우처 지갑용 관리페이지를 만들어보세요.**
(지난 과제에서 만들었던 기능을 페이지도만들고 api 도 개발해보았습니다.)
- [x] 특정 고객에게 바우처를 할당할 수 있습니다.
- [x] 고객이 어떤 바우처를 보유하고 있는지 조회할 수 있어야 합니다.
- [x] 고객이 보유한 바우처를 제거할 수 있어야 합니다. (여기까지 타임리프, api 모두)
- [x] 특정 바우처를 보유한 고객을 조회할 수 있어야 합니다. (해당 부분은 api 로 진행하였습니다.)

### [바우처 서비스 관리페이지(타임리프)]
![UI-시나리오](https://wooden-plate-047.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fca97975d-8662-4304-83ed-b06f6b134d40%2FKakaoTalk_Photo_2021-09-16-21-04-44.jpeg?table=block&id=263db812-b53a-443b-8b07-991de855d81f&spaceId=da06fe4c-dbc0-451e-a09d-8fe561a808ae&width=2540&userId=&cache=v2)
1. 프로그램을 시작하면 customerList 를 확인할 수 있다. (조회)
    1. 해당 페이지에서 고객을 추가하기위해 버튼을 눌러 입력창으로 이동 가능. (입력)
    2. 리스트중 원하는 고객을 누르면 고객이 가진 지갑들과 바우처 정보를 확인할 수 있다. (상세페이지)
    3. 상세페이지에서 원하는 바우처를 삭제할 수 있다.
        1. 새로운 바우처를 만들 수 있다.

### [바우처 지갑용 관리 페이지(JSON XML)]
#### 1. 전체 바우처 조회
##### Request
```http request
voucher API
GET http://localhost:8080/api/v1/vouchers
Accept: application/xml
```
##### Response (XML)
```xml
<List>
    <item>
        <voucherId>1b6b05c8-b55c-4be1-a872-41b032ded4ae</voucherId>
        <percent>13</percent>
        <voucherType>PERCENT_DISCOUNT_VOUCHER</voucherType>
        <createdAt>2021-09-15T22:10:02.421441</createdAt>
        <walletId>0a5bcd45-ab76-4159-a2c5-fab3845a977a</walletId>
        <amount>13</amount>
    </item>
    <item>
        <voucherId>23955ce1-beb6-4e0d-920d-4686f48760fa</voucherId>
        <amount>12</amount>
        <voucherType>FIXED_AMOUNT_VOUCHER</voucherType>
        <createdAt>2021-09-15T11:27:05.299406</createdAt>
        <walletId/>
    </item>
(...중략)
</List>
```
#### Request(JSON)
```
```http request
voucher API
GET http://localhost:8080/api/v1/vouchers
Accept: application/json
```
#### Response (JSON)
```json
[
  {
    "voucherId": "1b6b05c8-b55c-4be1-a872-41b032ded4ae",
    "percent": 13,
    "voucherType": "PERCENT_DISCOUNT_VOUCHER",
    "createdAt": "2021-09-15T22:10:02.421441",
    "walletId": "0a5bcd45-ab76-4159-a2c5-fab3845a977a",
    "amount": 13
  },
  {
    "voucherId": "23955ce1-beb6-4e0d-920d-4686f48760fa",
    "amount": 12,
    "voucherType": "FIXED_AMOUNT_VOUCHER",
    "createdAt": "2021-09-15T11:27:05.299406",
    "walletId": null
  }
...(중략)
]
```


### 2. 바우처 아이디로 조회 기능 (여기서부터는 json 만 기록.)

#### Request
```http request
GET http://localhost:8080/api/v1/vouchers/e8c54288-4aa2-4197-95b3-85f66c6f1143
```

#### Response
```json
{
  "voucherId": "e8c54288-4aa2-4197-95b3-85f66c6f1143",
  "percent": 10,
  "voucherType": "PERCENT_DISCOUNT_VOUCHER",
  "createdAt": "2021-09-14T23:48:41.361433",
  "walletId": null,
  "amount": 10
}
```


### 3. 바우처 추가기능
#### Request (body)로
```http request
POST http://localhost:8080/api/v1/vouchers
Content-Type: application/json
#Accept: application/xml
Accept: application/json
```
```json
{
  "amount": 100,
  "voucherType": "PERCENT_DISCOUNT_VOUCHER"
}
```
#### Response (추가후에 전체 리스트 내보내서 확인)
```json
[
  (..중략)
  {
    "voucherId": "27e6bb4e-c6eb-42b0-badc-92b591cc3ad5",
    "percent": 100,
    "voucherType": "PERCENT_DISCOUNT_VOUCHER",
    "createdAt": "2021-09-16T19:12:45.902289",
    "walletId": "020bb4e5-27fb-4ec0-8301-262852cc8668",
    "amount": 100
  }
]
```


### 4. 조건별 조회기능 (생성일자 별)
#### Request (query param)
```http request
GET http://localhost:8080/api/v1/vouchers/created-at?start=2021-09-14T23:48:41.361186&end=2021-09-15T09:48:03.748753
```
리턴 위와 동일한 형태로 기록 생략

### 5. 특정 할인 타입별 조회
```http request
GET http://localhost:8080/api/v1/vouchers/voucher-type?voucherType=PERCENT_DISCOUNT_VOUCHER
```
리턴 위와 동일한 형태로 기록 생략

### 6. 바우처 삭제
```http request
DELETE http://localhost:8080/api/v1/vouchers/0174f7ea-0dd7-422a-949e-8d3c9d92dedf
```
리턴 위와 동일한 형태로 기록 생략합니다.