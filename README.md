#  SpringBoot Part1 Weekly Mission

## (기본) **바우처 관리 애플리케이션**

- [x]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.
- [x]  바우처 관리 Command-line Application을 만들어본다.
    -
    참고: [https://dzone.com/articles/interactive-console-applications-in-java](https://dzone.com/articles/interactive-console-applications-in-java)
    - [x]  스프링부트 애플리케이션으로 만든다. (Web기능이 없이만듭니다. 즉, 서버가 띄지 않고 커맨드라인 애플리케이션으로 동작해야한다.)
    - [x]  프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.

  ```bash
  === Voucher Program ===
  Type **exit** to exit the program.
  Type **create** to create a new voucher.
  Type **list** to list all vouchers.
  ```

## 커맨드

- [x] 유효하지 않은 커맨드 입력에 대한 예외처리

### exit 커맨드

- [x] exit 커맨드를 지원한다.

### create 커맨드

- 바우처 생성 ✅
- 바우처 메모리에 저장 ✅

<br>

- [x] create 커맨드를 통해 바우처를 생성할수 있다. (FixedAmountVoucher, PercentDiscountVoucher)
- [x] 어떤 바우처를 생성할 건지 클라이언트가 선택
    - [x] 바우처 입력 가이드 출력
    - [x] 바우처 입력에 대한 유효성 검증
    - [x] 바우처 입력 예외 처리
- [x] FixedAmountVoucher
    - [x] 고정 할인 금액 입력
    - [x] 할인 금액 유효성 검증 (금액 > 0)
    - [x] 바우처 생성
    - [x] 바우처 저장
- [x] PercentAmountVoucher
    - [x] 고정 할인 비율(%) 입력
    - [x] 할인 비율 유효성 검증 (0 <= 비율 <= 100)
    - [x] 바우처 생성
    - [x] 바우처 저장

### list 커맨드

- [x] list 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
    - [x] 모든 바우처 조회

### 로그 & jar 파일 생성

- [x]  적절한 로그를 기록하고 `logback` 설정을해서 에러는 파일로 기록된다.
- [x]  실행가능한 `jar` 파일을 생성한다.

## (심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능

- [x]  메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
    - 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
    - [x] 텍스트 파일로 저장 [index, VoucherType, Amount/Ratio(%)]
    - [x] 콘솔 출력
- [x]  고객 블랙 리스트 명단을 작성한다.
- [x] customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고
  블랙 리스트조회 할 수있다 (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정합니다.)
- [x]  YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해본다.
