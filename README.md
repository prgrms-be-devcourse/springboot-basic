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

- [ ]  애플리케이션 단위 테스트 작성
- [x]  customers 테이블 정의 및 추가
- [x]  CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현
- [x]  voucher 테이블 정의 및 추가
- [x]  VoucherRepository 추가 및 JdbcTemplate을 사용해서 구현
- [ ]  기존 서비스 로직 테이블을 통해 동작하도록 수정

**(심화)** **바우처 지갑 생성**

- [ ]  특정 고객에게 바우처를 할당 기능 추가
- [ ]  해당 고객이 보유한 바우처 목록 조회 기능 추가
- [ ]  고객이 보유한 바우처 제거 기능 추가
- [ ]  특정 바우처를 보유한 고객 조회 기능 추가