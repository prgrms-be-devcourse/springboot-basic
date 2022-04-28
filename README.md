# SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

주차별 과제는 노션에서 확인하세요!
[노션에서 미션 확인가기](https://www.notion.so/backend-devcourse/Part1-3-38f57acca0dd490db11393701417943a)
<br/>

## SpringBoot Part1 Weekly Mission

(기본) **바우처 관리 애플리케이션**

- [x] Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.

- [X] 바우처 관리 Command-line Application을 만들어본다.

    - 참고: [https://dzone.com/articles/interactive-console-applications-in-java](https://dzone.com/articles/interactive-console-applications-in-java)
    - [X] 스프링부트 애플리케이션으로 만든다. (Web기능이 없이만듭니다. 즉, 서버가 띄지 않고 커맨드라인 애플리케이션으로 동작해야한다.)
    - [X] 프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.

  ```bash
  === Voucher Program ===
  Type **exit** to exit the program.
  Type **create** to create a new voucher.
  Type **list** to list all vouchers.
  ```

    - [X] create / list 커맨드를 지원한다.
        - create 커맨드를 통해 바우처를 생성할수 있다. (FixedAmountVoucher, PercentDiscountVoucher)
        - list 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
        - 바우처 정보를 매모리에 관리한다. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. (나중에 영속성을 가지도록 변경할거에요 걱정마세요!)

- [X] 적절한 로그를 기록하고 `logback` 설정을해서 에러는 파일로 기록된다.

- [X] 실행가능한 `jar` 파일을 생성한다.

(심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능

- [X] 메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
    - 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
- [X] 고객 블랙 리스트 명단을 작성한다.
- customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수있다 (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정합니다.)
- [ ] YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해본다.


### 구현할 기능 목록
- UI
    - [X] Text-IO 이용해 UI 구현하기
      - [X] 메뉴 출력 기능 개발
      - [X] 바우처 생성 커맨드 개발
      - [X] 바우처 조회 커맨드 개발
      - [X] 블랙 리스트 조회 커맨드 개발
- 핵심 로직
    - [X] Voucher 엔티티 만들기
        - [X] FixedAmountVoucher
        - [X] PercentDiscountVoucher
    - [X] MemoryVoucherRepository 만들기
        - [X] Voucher 저장
        - [X] Voucher 전체 조회
    - [X] MemoryVoucherService 만들기
        - [X] Voucher 생성 & 저장
        - [X] Voucher 조회
    - [X] VoucherController 만들기
    - [X] FileVoucherRepository 만들기
      - [X] Voucher 저장
      - [X] Voucher 전체 조회

### 1차 피드백
- [X] java 11로 변경
- [X] menu에 전략패턴 도입 생각해보기
- [X] 개행도 일관성있게 사용하기
- [X] Voucher serializable 이유
- [X] FileVoucherRepository 메서드 1depth로 줄여보기
- [X] ConstantString 관리 생각하기

### 2차 피드백
- [X] Voucher 관계 상속 관계로 수정하기
- [X] service 계층에서 createvoucher시 create 되게
- [X] Voucher 도메인의 FileVoucherRepository 의존성 낮추기.
    - DTO 객체를 이용해보자.
- [X] 파일에 로그를 남길 때 어느 레벨까지 남길지 고민해보기.
- [X] Test 메서드 명도 신경쓰기.
- [X] 동시 테스트 assertAll() 이용해보기.
- [X] BeforeEach, AfterEach 하나만 하도록 코드 수정
- [X] InstanceOf 지양하기

## SpringBoot Part2 Weekly Mission

**(기본)** **바우처 관리 애플리케이션**

- [X] 바우처 관리 애플리케이션에 단위테스트를 작성해보세요.
    - 가능한 많은 단위 테스트코드를 작성하려고 노력해보세요.
    - 엣지 케이스(예외 케이스)를 고려해서 작성해주세요.
    - Hamcrest 의 메쳐들을 다양하게 작성해보고 익숙해져 보세요.
- [X] 바우처 관리 애플리케이션에서도 과정에서 다루었던 고객을 적용해보세요.
    - customers 테이블 정의 및 추가
    - CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현
- [X] (1주차를 파일로 관리하게 했다.) 바우처 정보를 DB로 관리해보세요.
    - 바우처에 엔터티에 해당하는 vouchers 테이블을 한번 정의해보세요.
    - 바우처 레포지토리를 만들어보세요. (JdbcTemplate을 사용해서 구현)
    - 기존의 파일에서 바우처를 관리한 것을 vouchers 테이블을 통해서 CRUD가 되게 해보세요.

**(심화)** **바우처 지갑을 만들어보세요.**

- [X] 특정 고객에게 바우처를 할당할 수 있습니다.
- [X] 고객이 어떤 바우처를 보유하고 있는지 조회할 수 있어야 합니다.
- [X] 고객이 보유한 바우처를 제거할 수 있어야 합니다.
- [X] 특정 바우처를 보유한 고객을 조회할 수 있어야 합니다.


### 구현 기능 목록  (기본 요구사항)
- [X] JDBC 연결
- [X] customers table 만들기
- [X] JdbcCustomerRepository 만들기
  - [X] 회원 저장 기능 개발
  - [X] 회원 전체 조회 기능 개발
  - [X] 회원 전체 삭제 개발
  - [X] 회원 이름 수정 기능 개발
- [X] Customer 엔티티 개발
- [X] CustomerService 개발
  - [X] 회원 추가 기능 개발
  - [X] 회원 조회 기능 개발
- [X] ConsoleView에 기능 추가
  - [X] 회원 생성 기능 추가
  - [X] 회원 조회 기능 추가
- [X] vouchers table 만들기 (비정규화 방식으로 만듬)
- [X] JdbcVoucherRepository 만들기
  - [X] Voucher 전체 조회
  - [X] Voucher 저장
  - [X] Voucher 전체 삭제
  - [X] Voucher Id로 조회
- [X] 테스트 추가 & 전체적으로 리팩토링 (예외처리 등..)

### 구현 기능 목록 (심화 요구사항)
- [X] AOP 사용해 보기 (Logger)
- [X] vouchers table 수정 (참조키로 customers table와 연결)
- [X] 특정 고객에게 바우처 할당 기능
  - [X] vouchers table의 외래키 값 변경 (Repository)
  - [X] service 계층 개발
  - [X] ConsoleView, Controller 기능 추가
- [X] 고객이 어떤 바우처를 보유하고 있는지 조회할 수 있어야 합니다.
  - [X] 고객이 갖고 있는 바우처 Repository에서 조회 기능
  - [X] 고객이 갖고 있는 바우처 조회 기능 (Service)
  - [X] 고객이 갖고 있는 바우처 조회 기능 (Controller)
- [X] 고객이 보유한 바우처 제거 기능
  - [X] 특정 customer_id를 가진 voucher 제거 기능 (Repository)
  - [X] voucher 고객이 보유한 바우처 제거 기능 (Service)
  - [X] 고객이 보유한 바우처 제거 기능 (Controller)
- [X] 특정 바우처를 보유한 고객 조회 기능
  - [X] 특정 바우처를 가진 고객 조회 기능 개발 (Repository)
  - [X] 특정 바우처를 가진 고객 조회 기능 (Service)
  - [X] 특정 바우처를 가진 고객 조회 기능 (Controller)

## SpringBoot Part3 Weekly Mission

  **(기본) 바우처 서비스 관리페이지 개발하기**

- Spring MVC를 적용해서 thymeleaf 템플릿을 설정해보세요.
- 커맨드로 지원했던 기능을 thymeleaf를 이용해서 관리페이지를 만들고 다음 기능을 지원가능하게 해보세요
  - [X] 조회페이지
  - [X] 상세페이지
  - [X] 입력페이지
  - [X] 삭제기능
  

  **(기본) 바우처 서비스의 API 개발하기**

- Spring MVC를 적용해서 JSON과 XML을 지원하는 REST API를 개발해보세요
  - [X] 전체 조회기능
    - 엔티티 -> DTO Converter 도입 생각.
  - [X] 조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
    - [X] 특정 할인타입 바우처 조회
    - [X] 바우처 생성기간 이용 바우처 조회
  - [X] 바우처 추가기능
  - [X] 바우처 삭제기능
  - [X] 바우처 아이디로 조회 기능

- To do
  - REST API 고민
  - Controller Validation

    **(보너스) 바우처 지갑용 관리페이지를 만들어보세요.**

### 관리자 페이지 구현 목록 ###
- [X] 시작페이지 만들기 
- [X] 회원 관리 페이지 만들기
  - [X] 전체조회
  - [X] 상세페이지
  - [X] 입력페이지
  - [X] 삭제기능

### PR 궁금한 부분 ###
- 바우처 검색 API 설계에 대해서