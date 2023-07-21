# SpringBoot Basic Weekly Mission (Week1)

### 프로젝트 설계도 및 클래스 역할/책임
![image](https://github.com/sujjangOvO/springboot-basic/assets/89267864/901eac2e-ea62-444d-895d-724a05da51a5)

**1차 리팩토링 이후 설계도**
![image](https://github.com/sujjangOvO/springboot-basic/assets/89267864/eb6ad9d8-eb98-4fb3-9647-08546f71b90b)


### 구현한 기능
- [x]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.
- [x]  바우처 관리 Command-line Application을  만들어본다.
    - 참고: https://dzone.com/articles/interactive-console-applications-in-java
    - [x]  스프링부트 애플리케이션으로 만든다. (Web기능이 없이 만듭니다. 즉, 서버가 띄지 않고 커맨드라인 애플리케이션으로 동작해야한다.)
    - [x]  프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.
    
    ```bash
    === Voucher Program ===
    Type **exit** to exit the program.
    Type **create** to create a new voucher.
    Type **list** to list all vouchers.
    ```
    
    - [x]  create / list 커맨드를 지원한다.
        - create 커맨드를 통해 바우처를 생성할수 있다. (FixedAmountVoucher, PercentDiscountVoucher)
        - list 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
        - this바우처 정보를 메모리에 관리한다. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. (나중에 영속성을 가지도록 변경할거에요 걱정마세요!)
    
- [x]  적절한 로그를 기록하고 `logback` 설정을 해서 에러는 파일로 기록된다.
- [x]  실행가능한 `jar` 파일을 생성한다.
---
### Project History
- 데브코스 바우처 프로젝트로 코드리뷰를 받아 수정된 프로젝트입니다.
- Input, Output 인터페이스를 분리하고 구현한 Console 객체를 통해 입출력에 대한 부분에 대한 책임을 나누었습니다
- 예외 상황을 throw new로 처리하고 호출부분에서 예외를 처리합니다
- 메뉴 타입들을 열거형 enum으로 처리하였습니다
- CommandLineRunner를 구현하여 스프링 애플리케이션이 구동된 이후 실행되어야 하는 빈을 정의했습니다
- 매직넘버가 발생하는 부분을 수정하였습니다
- 인터페이스의 상위 타입을 선언하여 확장성을 고려하고 유지보수에 용이하도록 하였습니다
- 할인값 검증은 service에서 하지않고 생성자에서 검증하도록 하였습니다
- Consntant final class를 통해 메시지에 출력할 상수값을 모아두고 관리하도록 하였습니다
- domain 부분에서 출력 의존성을 가지면 안되므로 이 부분을 domain에 대한 정보를 인자로 넘기고 출력은 외부에서 진행하도록 변경 하였습니다
- try/catch문을 통해 코드레벨이 깊어지는 부분을 수정하기 위해 예외 처리가 필요한 부분에만 try/catch문을 적용하였습니다 따라서 추후 예외가 발생하는 부분을 찾기 쉬워집니다


---
# SpringBoot Basic Weekly Mission (Week2)

### 프로젝트 설계도 및 클래스 역할/책임
![image](https://github.com/prgrms-be-devcourse/springboot-basic/assets/89267864/79ae1764-fa84-40b6-99db-0f3136d7765d)


<수정>

Voucher와 Customer는 관련 없다고 생각

차라리 추후 Wallet이 추가될 때 Customer의 FK가 등록되어야 한다고 생각해서 Voucher와 Customer의 연관 관계를 삭제

<07.15 수정 설계도>
![image](https://github.com/prgrms-be-devcourse/springboot-basic/assets/89267864/996e40a7-8183-449d-9c9d-1cf288109398)

- voucher와 customer의 앱 확장성을 생각한다면 분리하는 것이 좋을 것 같아 Voucher, Service App을 각각 구현


---
**(기본)** **바우처 관리 애플리케이션**

- [ ]  바우처 관리 애플리케이션에 단위테스트를 작성해보세요.
    - 가능한 많은 단위 테스트코드를 작성하려고 노력해보세요.
    - 엣지 케이스(예외 케이스)를 고려해서 작성해주세요.
    - Hamcrest 의 메쳐들을 다양하게 작성해보고 익숙해져 보세요.
- [ ]  바우처 관리 애플리케이션에서도 과정에서 다루었던 고객을 적용해보세요.
    - customers 테이블 정의 및 추가
    - CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현
- [ ]  (1주차엔 파일로 관리하게 했다.) 바우처 정보를 DB로 관리해보세요.
    - 바우처에 엔터티에 해당하는 vouchers 테이블을 한번 정의해보세요.
    - 바우처 레포지토리를 만들어보세요. (JdbcTemplate을 사용해서 구현)
    - 기존의 파일에서 바우처를 관리한 것을 vouchers 테이블을 통해서 CRUD가 되게 해보세요.

**(심화)** **바우처 지갑을 만들어보세요.**

- 특정 고객에게 바우처를 할당할 수 있습니다.
- 고객이 어떤 바우처를 보유하고 있는지 조회할 수 있어야 합니다.
- 고객이 보유한 바우처를 제거할 수 있어야 합니다.
- 특정 바우처를 보유한 고객을 조회할 수 있어야 합니다.

---
### 작업 순서 List
- [ ] 메모리 바우처 관리 테스트 코드 작성
    - 바우처 메뉴 입력 테스트
    - 바우처 create 테스트
    - 바우처 crate 유효성 테스트
    - 바우처 list 테스트
- [x] customer 도메인 정의 및 추가
    - ~~customer는 voucher를 가진다 (FK)~~
- [x] jdbcVoucherRepository 추가 및 구현
    - id로 voucher 탐색
    - db에 voucher save
    - db의 id에 해당하는 voucher delete
    - db의 모든 voucher findAll
- [x] mysql db와 연결
    - jdbc template 설정
    - mysql에 voucher, customer entity 추가
- [x] 시작화면에 메뉴 customer/voucher로 분리하여 서비스 제공
- [x] jdbcCustomerRepository 추가 및 구현
    - id로 customer 탐색
    - db에 customer save
    - db의 id에 해당하는 customer delete
    - db의 모든 customer findAll
- [x] customerApp 구현
    - customer create 기능
    - customers list 기능
    - customer find by email 기능
    - customer delete by email 기능
    - customerApp과 customerService 연결
    - 중복되는 email에 대한 처리 필요
- [ ] jdbcCustomerRepository 테스트 코드 작성
    - id로 customer 탐색
    - db에 customer save
    - db의 id에 해당하는 customer delete
    - db의 모든 customer findAll
    - ~~고객이 가진 바우처 조회 테스트~~
- [ ] jdbcVoucherRepository 테스트 코드 작성
    - id로 voucher 탐색
    - db에 voucher save
    - db의 id에 해당하는 voucher delete
    - db의 모든 voucher findAll
  
