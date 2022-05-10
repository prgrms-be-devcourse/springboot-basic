# SpringBoot Basic Weekly Mission

스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.


주차별 과제는 노션에서 확인하세요!
[노션에서 미션 확인가기](https://www.notion.so/backend-devcourse/Part1-3-38f57acca0dd490db11393701417943a)


## SpringBoot Part3 Weekly Mission

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
        - [ ]  조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
        - [x]  바우처 추가기능
        - [x]  바우처 삭제기능
        - [x]  바우처 아이디로 조회 기능

  **(보너스) 바우처 지갑용 관리페이지를 만들어보세요.**


### 🧑‍💻 요구 사항과 구현 내용
![Screen-Recording-2022-04-28-at-2 06 46-AM](https://user-images.githubusercontent.com/74031333/165582195-996c6d76-8c21-4bb1-8001-4ad97304134a.gif)
##### Thymeleaf
* 바우처, 고객 페이지로 나뉩니다.
* 고객, 바우처 페이지에서 조회, 추가, 삭제, 수정 기능을 할 수 있습니다.
* 고객은 각자 가지고 있는 바우처를 조회, 생성, 삭제할 수 있습니다.
* 바우처는 특정 타입 바우처를 소유한 고객을 조회할 수 있습니다.

##### RESTAPI
* JSON형식으로 api를 보냅니다.

### 💡 PR 포인트 & 궁금한 점

---
##### 1. getter
팀미팅때 멘토님께서 말씀해주신대로 getter를 없애기 위해서 DTO를 사용하거나 최대한 getter를 사용해야 하는 것을 해당 객체에 책임을 지게 구현해보려고 노력했습니다.  
(ex - voucher의 validateType()메서드...)  
컨트롤러에서도 최대한 노출을 적게 하고 싶어 thymeleaf에 넘겨주는 것을 다 DTO로 해야한다 생각해 List<Customer>의 모든 값들을 List<customerDto>로 다 전달해 넘겨주었습니다.  
이런 방식으로 getter를 쓰지 않고 해결하는 방법이 맞는지 궁금합니다.. 분명 더 좋은 방법이 있을 것 같고 컨트롤러에서 이런 작업들을 하는 것이 틀린것 같아서 질문드립니다..!

##### 2. url

REST API이나 thymeleaf 에서 url을 설정하는 과정에서 vouchers나 customers을 구분하기 위해 일단 /vouchers , /customers 로 시작을 하고  
voucher에 관련된 내용은 vouchers/{voucherId} 나 vouchers/add 이런식으로 사용했습니다.  
제가 구현한 것에 voucher 페이지 에서 특정 바우처를 가지고 있는 customer들을 조회하는 기능이 있는데 이런 경우 /vouchers/{type}/customer 식으로 url을 설정했습니다.  
제가 생각해도 이런 방식으로 설정을 하면 사용하기 헷갈리고 어려울것 같다고 생각하는데 제가 지금 생각하는 것들은 다 이처럼 비슷하게 헷갈릴 것 같아 일단 이렇게 하였습니다.  
이런 상황에서는 어떤식으로 url을 설정해야 하는지 궁금합니다.


##### 항상 감사드립니다 🐯