# SpringBoot Part3 Weekly Mission

## 요구사항

- SpringBoot Part3 Weekly Mission

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

    - [x] 조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)

    - [x] 바우처 추가기능

    - [x] 바우처 삭제기능

    - [x] 바우처 아이디로 조회 기능

  **(보너스) 바우처 지갑용 관리페이지를 만들어보세요.**



## 추가된 기능 설명

- Type, Date, Type And Date 이용하여 바우처를 검색할 수 있습니다.
- Converter를 구현, 등록하여 url 파라미터로 온 String 값을 Date로 자동 변환, 검색을 수행할 수 있게 도움
- message 기능 이용하여 일관된 표현 사용
- bean validation으로 비어 있는 필드 찾음



## PR 포인트

- 