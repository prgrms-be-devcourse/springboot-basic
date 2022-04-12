# SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.<br>
[노션에서 미션 확인가기](https://www.notion.so/backend-devcourse/Part1-3-38f57acca0dd490db11393701417943a)<br>

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
- [ ]  실행가능한 `jar` 파일을 생성한다.

(심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능

- [ ]  메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
    - 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
- [ ]  고객 블랙 리스트 명단을 작성한다.
- customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수있다 (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정합니다.)
- [ ]  YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해본다.



part1
- kdtspringdemo
  - console
    - Input : 입력 값을 받는다(Scanner)
    - Menu : Menu enum
    - Output : print 출력 함수
  - voucher
    - storage
      - VoucherMemoryStorage : Memory 저장소
      - VoucherStorage : 저장소 interface
    - voucherdetail
      - FixedAmountVoucher : Voucher 구체클래스1
      - PercentDiscountVoucher : Voucher 구체클래스2
      - Voucher : Voucher 인터페이스
      - VoucherType : Voucher 타입
    - VoucherService : Voucher 관련 함수들 정의
  - AppProgram : 프로그램 while 문안에서 돌아간다 함수 순서들 표시
  - KdtSpringDemoApplication : Spring 시작

### PR Point
- 심화 부분과 테스트 코드는 작성하지 않았습니다. 2주차 테스트 코드 강의 후 추가 PR 올리도록 하겠습니다.
- enum 을 사용하여 menu 와 Voucher 타입을 지정하여 처리하였습니다. enum 사용이 능숙하지 않은데 맞는 방식으로 짰는지 궁금합니다.
- 객체지향적으로 짜면서 코드 컨벤션을 잘 하기 위해 노력하였습니다. 평가 부탁드립니다.
- input.java 클래스에서 inputAmount 메서드가 있습니다. Voucher 타입 선택 후 discount 금액 을 입력받는 부분입니다. 
  - 여기서 정수가 아닌 부분과 음수를 작성한 부분은 -1 로 처리하고 맞는 금액을 적은 경우 0보다 큰 정수값을 반환하게 하였고, 
  - 메서드 결과값으로 -1 이 나온경우 예외처리를 해주었습니다. 반환값을 금액(정수)를 반환하고 싶어서 -1 로 예외처리를 하였는데 
  - enum 으로 추가적인 클래스를 만들어서 처리하는게 맞나요? 이 부분을 위해 클래스 1개를 더 만드는 것이 맞을까란 생각에 만들지 않았습니다.
