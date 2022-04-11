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


## SpringBoot Part2 Weekly Mission

**(기본)** **바우처 관리 애플리케이션**

- [ ] 바우처 관리 애플리케이션에 단위테스트를 작성해보세요.
    - 가능한 많은 단위 테스트코드를 작성하려고 노력해보세요.
    - 엣지 케이스(예외 케이스)를 고려해서 작성해주세요.
    - Hamcrest 의 메쳐들을 다양하게 작성해보고 익숙해져 보세요.
- [ ] 바우처 관리 애플리케이션에서도 과정에서 다루었던 고객을 적용해보세요.
    - customers 테이블 정의 및 추가
    - CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현
- [ ] (1주차를 파일로 관리하게 했다.) 바우처 정보를 DB로 관리해보세요.
    - 바우처에 엔터티에 해당하는 vouchers 테이블을 한번 정의해보세요.
    - 바우처 레포지토리를 만들어보세요. (JdbcTemplate을 사용해서 구현)
    - 기존의 파일에서 바우처를 관리한 것을 vouchers 테이블을 통해서 CRUD가 되게 해보세요.

**(심화)** **바우처 지갑을 만들어보세요.**

- 특정 고객에게 바우처를 할당할 수 있습니다.
- 고객이 어떤 바우처를 보유하고 있는지 조회할 수 있어야 합니다.
- 고객이 보유한 바우처를 제거할 수 있어야 합니다.
- 특정 바우처를 보유한 고객을 조회할 수 있어야 합니다.