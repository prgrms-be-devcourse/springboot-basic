# SpringBoot Basic Weekly Mission

스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

## 요구 사항

**(기본)** **바우처 관리 애플리케이션**

- 바우처 관리 애플리케이션에 단위테스트를 작성해보세요.
    - [x] 가능한 많은 단위 테스트코드를 작성하려고 노력해보세요.
    - [x] 엣지 케이스(예외 케이스)를 고려해서 작성해주세요.
    - [x] Hamcrest 의 메쳐들을 다양하게 작성해보고 익숙해져 보세요. -> AssertJ 로 대신하였습니다.
- 바우처 관리 애플리케이션에서도 과정에서 다루었던 고객을 적용해보세요.
    - [x] customers 테이블 정의 및 추가
    - [x] CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현
- (1주차엔 파일로 관리하게 했다.) 바우처 정보를 DB로 관리해보세요.
    - [x] 바우처에 엔터티에 해당하는 vouchers 테이블을 한번 정의해보세요.
    - [x] 바우처 레포지토리를 만들어보세요. (JdbcTemplate을 사용해서 구현)
    - [x] 기존의 파일에서 바우처를 관리한 것을 vouchers 테이블을 통해서 CRUD가 되게 해보세요.

**(심화)** **바우처 지갑을 만들어보세요.**

- [x] 특정 고객에게 바우처를 할당할 수 있습니다.
- [x] 고객이 어떤 바우처를 보유하고 있는지 조회할 수 있어야 합니다. - [x]고객이 보유한 바우처를 제거할 수 있어야 합니다.
- [x] 특정 바우처를 보유한 고객을 조회할 수 있어야 합니다.

## 클래스 다이어그램

![voucher_w2_uml](https://user-images.githubusercontent.com/82152173/203047408-0aec6d93-5c28-42ae-aed9-5cc4831972a4.PNG)

## ERD

![voucher_w2_erd](https://user-images.githubusercontent.com/82152173/203057234-396bc221-7a8d-49e7-9fba-14d1a7044701.PNG)

## 주요 클래스 설명

- console
    - io
        - `ConsoleInput` : 콘솔에서의 입력을 담당합니다.
        - `ConsoleOutput` : 콘솔에서의 출력을 담당합니다.
        - `Input` : 입력의 인터페이스입니다.
        - `Output` : 출력의 인터페이스입니다.
    - run
        - `AppPower` : 프로그램이 계속 실행되도 되는지 여부를 판별하는 역할, 프로그램 종료 요청 역할을 가집니다.
        - `Command` : 사용자가 콘솔에 입력할 수 있는 명령어들을 관리합니다.
        - `Message` : 프로그램 동작 과정에서 사용자에게 출력하는 메시지를 관리합니다.
        - `PentaConsumer` : Command 클래스에서 사용하는 함수형 인터페이스로 사용자의 입력 명령에 따른 동작을 실행합니다.
    - `ConsoleVoucherApp` : 사용자의 명령에 따른 바우처 관리 프로그램 동작 흐름을 담당합니다.

- core
    - exception
        - `DataUpdateException` : 저장, 수정, 삭제 같은 데이터 갱신에 실패했을지 발생하는 예외입니다.
        - `EmptyBufferException` : BufferedReader, BufferedWriter 사용 시 처리해야하는 IOException 을 구체적으로 전환하는 예외입니다.
        - `ExceptionMessage` : 예외 발생 시 출력하는 메시지들을 관리합니다.
        - `NotFoundException` : 처리하고자하는 데이터가 없을 때 발생하는 예외입니다.
    - util
        - `JdbcTemplateUtil` : JdbcTemplate 를 사용하여 DB 작업 중 필요한 매핑 작업들을 관리합니다.

- domain
    - customer
        - model
            - `Customer` : 고객 정보를 가지고 있는 클래스이고, id, 타입, 생성시간, 최종 수정시간을 가지고 있습니다.
            - `CustomerType` : 고객 타입을 관리합니다. NORMAL, BLACKLIST 2가지 타입으로 관리합니다.
        - repository
            - `CustomerRepository` : 고객 저장소의 인터페이스입니다.
            - `CustomerSQL` : JdbcCustomerRepository 에서 필요한 SQL 문을 관리합니다.
            - `FileCustomerRepository` : 파일로 고객들의 정보를 관리합니다. file 프로파일에서 동작합니다.
            - `JdbcCustomerRepository` : JDBC 를 이용하여 RDBMS 으로 고객 정보를 관리합니다. MySQL 을 이용합니다. jdbc, test 프로파일에서 동작합니다.
        - service
            - `CustomerService` : 고객 관련 서비스를 담당합니다.
    - voucher
        - model
            - `FixedDiscountVoucher` : 고정형 할인 바우처입니다.
            - `PercentDiscountVoucher` : 비율형 할인 바우처입니다.
            - `Voucher` : 바우처들의 정보를 가지고 있는 클래스입니다. 추상 클래스이며 타입에 따라 확장 가능합니다.
            - `VoucherType` : 바우처 타입을 관리하는 enum 입니다.
        - repository
            - `FileVoucherRepository` : 파일로 바우처 정보를 관리합니다. file 프로파일에서 동작합니다.
            - `JdbcVoucherRepository` : JDBC 를 이용하여 RDBMS 으로 바우처 정보를 관리합니다. MySQL 을 이용합니다. jdbc, test 프로파일에서 동작합니다.
            - `MemoryVoucherRepository` : 메모리에서 바우처 정보를 관리합니다. local 프로파일에서 동작합니다.
            - `VoucherRepository` : 바우처 저장소의 인터페이스입니다.
            - `VoucherSQL` : JdbcVoucherRepository 에서 필요한 SQL 문을 관리합니다.
        - service
            - `VoucherService` :  바우처 관련 서비스를 담당합니다.
        - util
            - `VoucherFactory` : 사용자의 입력 받은 값에 따라 바우처 구현체를 만들어줍니다.
    - wallet
        - model
            - `Wallet` : 고객에게 할당된 바우처 정보를 관리합니다.
        - repository
            - `JdbcWalletRepository` : JDBC 를 이용하여 RDBMS 으로 바우처 지갑 정보를 관리합니다. MySQL 을 이용합니다. jdbc, test 프로파일에서 동작합니다.
            - `WalletRepository` : 바우처 지갑 저장소의 인터페이스입니다.
            - `WalletSQL` : JdbcWalletRepository 에서 필요한 SQL 문을 관리합니다.
        - service
            - `WalletService` : 바우처 지갑 관련 서비스를 담당합니다.
