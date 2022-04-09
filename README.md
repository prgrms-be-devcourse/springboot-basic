# SpringBoot Basic 1주차 미션

# 🚀 요구 사항

---

- [x]  바우처 관리 Command-line Application을 만들어본다.
    - [x]  스프링부트 애플리케이션으로 만든다.
    - [x]  프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.
    - [x]  create / list 커맨드를 지원한다.
- [x]  적절한 로그를 기록하고 `logback` 설정을해서 에러는 파일로 기록된다.
- [x]  실행가능한 `jar` 파일을 생성한다.

(심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능

- [x]  메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
    - 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
- [x]  고객 블랙 리스트 명단을 작성한다.
- customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수있다
- [x]  YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해본다.

# 🚀 구현 내용

---

## Engine 패키지

애플리케이션의 핵심 비즈니스 로직을 담당합니다.

### Input, Output

* 애플리케이션의 입출력을 담당합니다.

#### Console

* CLI를 통해 입출력을 담당합니다.

### Demo

* 애플리케이션의 전체적인 실행 흐름을 제어합니다.
* **Input, Output, VoucherService, BlackListService** 에 구체적인 기능 구현을 위임합니다.
* 최종적인 예외 처리를 담당합니다.
    * 사용자의 단순한 실수 등 치명적이지 않은 예외를 **VoucherException**으로 받아 처리합니다.
    * 그 외의 예외는 치명적인 예외로 분류하고, 로그 파일에 따로 입력하고 애플리케이션을 종료합니다.

### Voucher

* 고유 식별자인 UUID를 가집니다.

* 기본 생성자를 호출하는 정적 팩터리 메서드가 존재합니다.

  **FixedAmountVoucher**

  ​ 고정된 금액 만큼 할인해주는 Voucher입니다.

  **PercentDiscountVoucher**

  ​ 고정된 비율만큼 할인해주는 Voucher입니다.

### VoucherMapper

* 사용자로부터 입력받은 특정한 값에 해당하는 Voucher 구현체와 연결시켜주는 enum입니다.
* Voucher 구현체에 대한 메타데이터, 정적 팩터리 메서드를 멤버로 갖고 있습니다.

### VoucherService

* Demo와 VoucherRepository를 중개하면서 Voucher에 관한 핵심 비즈니스 로직을 담당합니다.
* 현재는 Voucher의 생성과 저장된 Voucher들을 불러오는 기능을 갖고 있습니다.

### VoucherRepository

* 생성된 Voucher를 저장소에 저장하거나, 저장된 Voucher를 애플리케이션으로 불러오는 기능을 담당합니다.

#### MemoryVoucherRepository

* Profile이 'local'일 경우 bean에 등록됩니다.

* 메모리에 Voucher를 저장합니다.
* 애플리케이션이 종료되면 저장된 Voucher들은 사라집니다.

#### FileVoucherRepository

* Profile이 'dev'일 경우 bean에 등록됩니다.

* 자바 애플리케이션이 실행되는 경로에 voucher-db.txt 파일에 Voucher를 저장합니다.
* 애플리케이션이 실행될 떄 해당 경로에 파일이 없으면 새로 파일을 생성합니다.
* 파일이 읽어서 Voucher에 대한 정보를 메모리에 보관합니다. (Cache 방식)
* 생성된 Voucher를 파일과 Cache에 저장합니다.

### BlackList 패키지

* 블랙리스트 관련 비즈니스 로직을 담당합니다.
* 애플리케이션이 시작할 때 resource에 있는 customer_blacklist.csv 파일을 읽어 들여서
* 메모리에 로드한 후 Demo의 요청이 있을 때 전달합니다.

### Exception 패키지

#### VoucherException

* 애플리케이션에서 발생하는 치명적인 예외가 아닌 예외들을 다룹니다.
* VoucherException를 상속받은 예외들은 구체적인 예외 상황을 메시지로 저장합니다.
* 예외 메시지의 내용을 사용자 인터페이스에 출력합니다.

## Configuration 패키지

* 애플리케이션에서 활용하는 설정 정보들을 보관합니다.

  ### FileDBProperties

* Profile이 'dev'일 경우 bean에 등록됩니다.
* application-dev.yaml에 있는 정보들을 읽고 저장합니다.
* 현재는 Voucher를 보관할 file이름을 필드로 갖고 있으며, FileVoucherRepository에서 이를 활용합니다.

##  









