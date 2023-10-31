# SpringBoot Basic Weekly Mission

## Week 1

(기본) **바우처 관리 애플리케이션**

- [x]  Gradle 로 프로젝트를 실제로 구성 및 실행
- [x]  바우처 관리 Command-line Application 생성
    - [x]  스프링부트 애플리케이션으로 생성(Web기능이 없이 커맨드라인 애플리케이션으로 동작)
    - [x]  프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려줌

```bash
=== Voucher Program ===
Type **exit** to exit the program.
Type **create** to create a new voucher.
Type **list** to list all vouchers.
```

- [x]  create / list 커맨드를 지원
    - create 커맨드를 통해 바우처 생성(FixedAmountVoucher, PercentDiscountVoucher)
    - list 커맨드를 통해 만들어진 바우처 조회
    - 바우처 정보를 메모리에 관리

- [x]  적절한 로그를 기록하고 `logback` 설정을 해서 에러는 파일로 기록
- [x] 실행가능한 jar 파일 생성

(심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능

- [x]  메모리 관리가 아닌 파일로 관리가 되는 Repository 생성
    - 기존 메모리 레포지토리는 지우지 않고 개발 프로파일에서만 동작
- [x]  고객 블랙 리스트 명단을 작성
    - customer.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽어 블랙 리스트조회(추가 기능 필요 x)
- [x]  YAML 프라퍼티를 만들고 설정 지정

## Week 2

**(기본)** **바우처 관리 애플리케이션**

- [x]  애플리케이션 단위 테스트 작성
- [x]  customers 테이블 정의 및 추가
- [x]  CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현
- [x]  voucher 테이블 정의 및 추가
- [x]  VoucherRepository 추가 및 JdbcTemplate을 사용해서 구현
- [x]  기존 서비스 로직 테이블을 통해 동작하도록 수정 및 Voucher CRUD 기능 추가

**(심화)** **바우처 지갑 생성**

- [x]  특정 고객에게 바우처를 할당 기능 추가
- [x]  해당 고객이 보유한 바우처 목록 조회 기능 추가
- [x]  고객이 보유한 바우처 할당 해제 기능 추가
- [x]  특정 바우처를 보유한 고객 조회 기능 추가

## Week 3

** 바우처 서비스 관리페이지 개발**

- Spring MVC를 적용해서 thymeleaf 템플릿을 설정하여 다음 기능을 지원하는 관리 페이지 개발
    - [ ]  조회페이지
    - [ ]  상세페이지
    - [ ]  입력페이지
    - [ ]  삭제기능

** 바우처 서비스의 API 개발하기**

- Spring MVC를 적용해서 JSON과 XML을 지원하는 REST API를 개발
    - [ ]  전체 조회기능
    - [ ]  조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
    - [ ]  바우처 추가기능
    - [ ]  바우처 삭제기능
    - [ ]  바우처 아이디로 조회 기능

- [ ] (심화) 바우처 지갑용 관리페이지 개발