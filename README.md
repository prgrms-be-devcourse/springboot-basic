# w3-SpringBoot_Part_A

## 1️⃣ D2 - 바우처 관리 Command-line Application 구현

### 구현 사항

- [x] CommandLineApplication 클래스를 작성한다.
    - `adapter/CommandLineApplicaton.java`
- [x] AnnotationConfigApplicationContext 를 이용해서 IoC 컨테이너를 생성하고 서비스, 레포지토리를 빈으로 등록한다.
    - `adapter/AppConfig.java`
- [x] create / list 커맨드를 지원한다.
    - [x] create 커맨드를 통해 바우처를 생성할수 있습니다. (FixedAmountVoucher, PercentDiscountVoucher)
        - `application/VoucherService.java` 의 `createVoucher()`를 통해 바우처를 생성
    - [x] list 커맨드를 통해 만들어진 바우처를 조회할 수 있습니다.
        - `adapter/CommandLineApplication.java`의 `List<Voucher>`로 바우처 저장하고 list 메소드로 구현

### 예외 처리

- input validation
  - [x] command
  - [x] voucher type
  - [x] voucher discount percent(PercentDiscountVoucher)
  - [x] voucher discount amount(FixedAmountVoucher, Order::discount)

### 리팩토링

- [x] .mvn gitignore 추가
- [x] CommandLineApplication 내 기능 분리 -> VoucherController
  - [x] input(Receiver), output(Printer) 분리
  - [x] BufferedReader, BufferedWriter -> Scanner와 sout으로 변경 (가독성)
- [x] Command 종류를 enum으로 변환
- [x] input validation 처리(VoucherController)
- [ ] voucher type 입력에 전략 패턴 적용

___

## 2️⃣ D3 - 파일 관리 Repository 구현

### 구현 사항

- [x] ComponentScan을 통해 bean 등록한다.
- [x] file로 관리되는 Repository를 만든다.
  - ApplicationContext 전후처리로 바우처 저장

### 리팩토링

- [x] voucher 저장소 controller -> repository
- [x] voucher 사용 후 -> 사용 전으로 되게 수정 (VoucherService, Voucher)
- [x] try with resource로 구현해보기

---

## 3️⃣ D4 - 고객 블랙 리스트 명단 작성

### 구현 사항

- [ ] customer_blacklist.csv를 만든다.
  - read만
- [ ] YAML 프로퍼티 생성, 설정해본다.
- [ ] MemoryVoucherRepository를 개발 프로파일에서만 동작하게 한다.
