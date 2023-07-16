바우처 과제 3차

### 1. 그림 그리기 및 소개하기

![image](/doc/바우처설계도_w3.png)


</br>

### 2. 요구사항

**(기본) 바우처 서비스 관리페이지 개발하기**

- Spring MVC를 적용해서 thymeleaf 템플릿을 설정해보세요.
- 커맨드로 지원했던 기능을 thymeleaf를 이용해서 관리페이지를 만들고 다음 기능을 지원가능하게 해보세요
   - [ ]  조회페이지
   - [ ]  상세페이지
   - [ ]  입력페이지
   - [ ]  삭제기능

**(기본) 바우처 서비스의 API 개발하기**

- Spring MVC를 적용해서 JSON과 XML을 지원하는 REST API를 개발해보세요
   - [ ]  전체 조회기능
   - [ ]  조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
   - [ ]  바우처 추가기능
   - [ ]  바우처 삭제기능
   - [ ]  바우처 아이디로 조회 기능

</br>

### 3. 작업 순서 목록

- [ ] 설계도, 요구사항, 작업 수행 목록 작성
- [ ] spring-web, thymeleaf 의존성 추가

</br>

- [ ] DefaultHandlerExceptionResolver에 application/xml을 인식할 수 있도록 설정
- [ ] 바우처 도메인 생성 기간 필드 추가 및 생성 기간별 조회기능 추가
- [ ] 바우처 할인 타입별 조회 기능 추가
- [ ] 바우처 생성에 대한 RESTController 메서드를 POST 메서드로 구현
- [ ] RestController ExceptionHandler 추가
- [ ] 바우처 전체조회에 대한 RESTController 메서드를 GET 메서드로 구현
- [ ] 바우처 아이디 조건 조회 기능에 대한 RESTController 메서드를 GET 메서드와 path variable을 사용해 구현
- [ ] 바우처 할인타입 조건 조회 기능에 대한 RESTController 메서드를 GET 메서드와 path variable을 사용해 구현
- [ ] 바우처 생성기간 조건 조회 기능에 대한 RESTController 메서드를 GET 메서드와 Query Parameter을 사용해 구현
- [ ] 바우처 아이디 조건 삭제 기능에 대한 RESTController 메서드를 GET 메서드와 path variable을 사용해 구현

</br>

- [ ] 바우처 생성을 위한 입력 페이지 및 Controller 메서드 구현
- [ ] ViewController ExceptionHandler 추가
- [ ] 바우처 조회 페이지 및 Controller 메서드 구현
- [ ] 바우처 아이디 조건 삭제 Controller 메서드 구현 및 전체 조회 리다이렉트
- [ ] 바우처 상세 페이지 및 Controller 메서드 구현 
