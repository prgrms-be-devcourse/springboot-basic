# SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

## 1주차 요구 사항
- 바우처 관리 Command-line Application을 만든다.
- create / list 커맨드를 지원한다.
    - create 커맨드를 통해 바우처를 생성할수 있다.
    - list 커맨드를 통해 만들어진 바우처를 조회할 수 있다.

## 2주차 요구사항
- 단위 테스트를 작성한다.
- 고객을 적용한다.
  - customers 테이블 정의 및 추가한다.
  - CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현한다.
- 바우처 정보를 DB로 관리한다.
  - 바우처에 엔터티에 해당하는 vouchers 테이블을 정의한다.
  - JdbcTemplate을 사용해서 바우처 레포지토리를 만든다.
  - vouchers 테이블을 통해서 CRUD를 할 수 있다.

## 3주차 요구사항
- 커맨드로 지원했던 기능을 thymeleaf를 이용해서 다음 기능을 지원할 수 있다.
  - 조회페이지
  - 상세페이지
  - 입력페이지
  - 삭제기능
- Spring MVC를 적용해서 JSON을 지원하는 REST API를 개발한다.
  - 조회페이지
  - 상세페이지
  - 입력페이지
  - 삭제기능
