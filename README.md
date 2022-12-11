# SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

## 요구 사항
(기본) **바우처 관리 애플리케이션**
- [x]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.
- [x]  바우처 관리 Command-line Application을 만들어본다.
    - 참고: [https://dzone.com/articles/interactive-console-applications-in-java](https://dzone.com/articles/interactive-console-applications-in-java)
    - [x]  스프링부트 애플리케이션으로 만든다. (Web기능이 없이만듭니다. 즉, 서버가 띄지 않고 커맨드라인 애플리케이션으로 동작해야한다.)
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
        - 바우처 정보를 매모리에 관리한다. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. (나중에 영속성을 가지도록 변경할거에요 걱정마세요!)

- [x]  적절한 로그를 기록하고 `logback` 설정을해서 에러는 파일로 기록된다.
- [x]  실행가능한 `jar` 파일을 생성한다.

(심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능
- [x]  메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
    - 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
- [x]  고객 블랙 리스트 명단을 작성한다.
    - customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수있다 (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정합니다.)
- [x]  YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해본다.

## 클래스 다이어그램
![voucher_uml](https://user-images.githubusercontent.com/82152173/200182636-46f8ae9f-4ece-4a7d-b570-584e90e3f5d8.PNG)

## 주요 클래스 설명
- controller
    - `Command` : 사용자가 입력할 수 있는 create, list, blacklist, exit 명령어들을 관리합니다.
    - `Message` : 프로그램 동작 과정에서 사용자에게 출력하는 메시지를 관리합니다.
    - `VoucherController` : 사용자의 명령에 따른 바우처 관리 프로그램 동작 흐름을 담당합니다.

- domain
    - customer
        - `Customer` : 고객 정보를 가지고 있는 클래스이고, id와 type을 가지고 있습니다.
        - `CustomerType` : 고객 타입을 관리합니다. Normal, Blacklist 2가지 타입으로 관리합니다.
        - `FileCustomerRepository` : 파일로 고객들의 정보를 저장, 조회합니다.  현재는 블랙리스트 타입 고객만 조회하는 기능만 가집니다.
        - `CustomerService` : 고객 관련 서비스를 담당합니다.
    - voucher
        - `FixedDiscountVoucher` : 고정형 할인 바우처입니다.
        - `PercentDiscountVoucher` : 비율형 할인 바우처입니다.
        - `VoucherType` : 바우처 타입을 별도로 관리하는 enum입니다.
        - `FileVoucherRepository` : 파일로 바우처를 저장하고, 파일에서 바우처를 조회합니다. dev, test 프로파일에서 동작합니다.
        - `MemoryVoucherRepository` : 메모리에서 바우처를 저장, 조회합니다. local 프로파일에서 동작합니다.
        - `VoucherService` :  바우처 관련 서비스를 담당합니다.
        - `Validator` : 사용자가 create 명령으로 바우처를 생성할 때, 잘못된 입력값을 넣었는지 검증합니다.
        - `VoucherFactory` : 입력 받은 값에 따라 바우처 구현체를 만들어줍니다.
- exception
    - `ExceptionMessage` : 예외 발생 시 출력하는 메시지들을 관리합니다.
    - `OutOfPercentRangeException` : PercentDiscountVoucher 생성 시, 할인 비율로 0~100 이외의 값을 넣었을 때 발생합니다.
    - `VoucherNotFoundException` : VoucherRepository 에서 바우처를 조회할 수 없을 경우 발생합니다.
    - `WrongCommandException` : 사용자가 create, list, blacklist, exit 가 아닌 잘못된 명령어를 입력했을 때 발생합니다.
    - `WrongDiscountTypeException` : 할인 입력 값이 숫자가 아닐 경우 발생합니다.
    - `WrongVoucherTypeException` : 생성하고자 하는 바우처 타입을 잘못 입력했을 시 발생합니다.
- io
    - `ConsoleInput` : 콘솔에서의 입력을 담당합니다.
    - `ConsoleOutput` : 콘솔에서의 출력을 담당합니다.
