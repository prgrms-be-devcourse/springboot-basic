# SpringBoot Basic Weekly Mission

## SpringBoot Part1 Weekly Mission

(기본) **바우처 관리 애플리케이션**

- [ ]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜
  본다.
- [ ]  바우처 관리 Command-line Application을 만들어본다.
    - 참고: https://dzone.com/articles/interactive-console-applications-in-java
    - [ ]  스프링부트 애플리케이션으로 만든다. (Web기능이 없이 만듭니다. 즉, 서버가 띄지 않고 커맨드라인 애플리케이션으로 동작해야한다.)
    - [ ]  프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.

  ```bash
  === Voucher Program ===
  Type **exit** to exit the program.
  Type **create** to create a new voucher.
  Type **list** to list all vouchers.
  ```

    - [ ]  create / list 커맨드를 지원한다.
        - create 커맨드를 통해 바우처를 생성할수 있다. (FixedAmountVoucher, PercentDiscountVoucher)
        - list 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
        - this바우처 정보를 메모리에 관리한다. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. (나중에 영속성을 가지도록 변경할거에요 걱정마세요!)

- [ ]  적절한 로그를 기록하고 `logback` 설정을 해서 에러는 파일로 기록된다.
- [ ]  실행가능한 `jar` 파일을 생성한다.

(심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능

- [ ]  메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
    - 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
- [ ]  고객 블랙 리스트 명단을 작성한다.
    - customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수있다 (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정합니다.)
- [ ]  YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해본다.

### 👩‍💻 구조도
![KakaoTalk_Photo_2023-10-16-23-53-54](https://github.com/voidmelody/springboot-basic/assets/63526857/76f0bd84-3e1f-47e8-8b98-1c28927d0f0e)

### ✅ PR 포인트 & 궁금한 점

* 최대한 객체지향을 신경써가며 작성했습니다. 코드들에서 해당하지 못하는 부분에 대해 지적 부탁드립니다.
* view <-> controller <-> service <-> repository 흐름으로 작성했습니다.
* Enum을 활용해서 VoucherType에 역할과 책임을 부여했습니다. 해당 Enum이 타입에 맞게 생성자를 호출하거나 인스턴스 여부를 파악해줌으로써 확장과 수정에 유연하게 대처할 수 있습니다.
* 현재 무한 반복문을 호출하다가 사용자로부터 종료 입력이 되면 system.exit(0)으로 프로그램이 종료되게 구현을 했는데, 해당 구현보다 더 좋은 방법이 있을지 궁금합니다.
