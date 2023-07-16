## 📌 과제 설명 <!-- 어떤 걸 만들었는지 대략적으로 설명해주세요 -->

(기본) **바우처 관리 애플리케이션**

- [x]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.
- [x]  바우처 관리 Command-line Application을 만들어본다.
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
        - 바우처 정보를 매모리에 관리한다. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. (나중에 영속성을 가지도록 변경할거에요 걱정마세요!)
- [ ]  적절한 로그를 기록하고 `logback` 설정을해서 에러는 파일로 기록된다.
- [ ]  실행가능한 `jar` 파일을 생성한다.

(심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능

- [ ]  메모리 관리가 아닌   파일로 관리가 되는 Repository를 한번 만들어보세요.
    - 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
- [ ]  고객 블랙 리스트 명단을 작성한다.
- customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수있다 (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정합니다.)
- [ ]  YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해본다.

## 👩‍💻 요구 사항과 구현 내용 <!-- 기능을 Commit 별로 잘개 쪼개고, Commit 별로 설명해주세요 -->
**클래스 다이어그램**

1. [링크](https://www.nextree.co.kr/p6753/)의 클래스 다이어그램 표기법을 참고하여 설계도를 작성했습니다.


2. 클래스 설명


- VoucherApplication
  - SpringBoot Application을 실행하는 클래스입니다.

    
- AppConfiguration
  - Application에 사용되는 Bean을 생성하고, 생성자 의존성 주입 패턴으로 의존성을 설정하는 클래스입니다.


 - CommandLineApplication
   - Console 객체와 VoucherService 빈을 사용해 Application의 실행 흐름을 담당한다.
 

- Console
  - Application의 입출력을 담당한다.


- ModeType
  - 사용자가 Application에서 선택할 수 있는 기능을 표현하기 위해 사용된다.


- VoucherService
  - Application의 기능 수행을 담당한다.
  - Voucher 생성, Voucher 전체 조회 


- Voucher
  - Voucher 엔티티의 기능을 정의한 인터페이스이다.
  - 엔티티라는 용어는 데브코스 강의를 수강하며 학습한 도메인 주도 설계의 용어를 사용했다.
  - 개방패쇄 원칙을 지키며 Voucher의 다형성을 보장할 수 있다.


- FixedAmountVoucher
  - Voucher 인터페이스의 구현체이다.
  - 고정된 금액만큼 할인하는 voucher이다.


- PercentDiscountVoucher
  - Voucher 인터페이스의 구현체이다.
  - 특정 비율만큼 할인하는 voucher이다.


- VoucherType
    - Voucher의 종률를 표현하기 위해 사용된다.

    
- VoucherRepository 
  - 생성된 voucher 정보를 저장하기 위해 사용하는 인터페이스이다.
  - 개방패쇄 원칙을 지키며 Repository의 다형성을 보장할 수 있다.


- MemoryVoucherRepository 
  - VoucherRepository의 구현체이다.
  - Map 자료구조를 사용하여 Voucher 정보를 저장하는 인메모리 저장소이다.


- FileVoucherRepository 
  - VoucherRepository의 구현체이다.
  - 파일을 사용하여 Voucher 정보를 저장하는 저장소이다.


- FieldExtractor
  - 객체의 멤버변수에 대한 정보를 Object 배열로 변환한다.


- LineAggregator 
  - FieldExtractor 로부터 반환된 Object 배열을 문자열로 바꿔주는 인터페이스이다.
  - 개방패쇄 원칙을 지키며 LineAggregator의 다형성을 보장할 수 있다.


- SpaceLineAggregator
  - LineAggregator의 구현체이다.
  - 필드값 사이를 공백문자로 구분하여 문자열을 생성한다. 

    
- LineTokenizer
    - 문자열을 파씽하는 기능을 수행하는 인터페이스이다.


- SpaceLineTokenizer
  - LineTokenizer의 구현체이다.
  - 공백을 기준으로 문자열을 파씽하는 기능을 수행한다.

    
- FieldSetMapper
    - 파씽된 문자열을 바탕으로 인스턴스를 생성한다.
    - File로 부터 저장된 Voucher 정보를 객체로 불러올때 사용한다.

    
- LineMapper
    - LineTokenizer와 FieldSetMapper를 사용해 문자열을 객체로 변환하는 기능을 수행한다.


3. 프로그램을 구현하는 도중 설계도를 먼저 작성하여 PR 보냅니다. 프로그램이 동작하긴하지만, 입력값 검증 및 예외처리가 되지 않았습니다. 피드백 받고 개선하겠습니다. 

4. 다른 요구사항을 모두 충족한 뒤 FileRepository 관련 내용을 구현할 예정입니다.


## ✅ PR 포인트 & 궁금한 점 <!-- 리뷰어 분들이 집중적으로 보셨으면 하는 내용을 적어주세요 -->

1. 멤버변수는 어떻게 초기화 해주는 것이 좋을까요? 예를 들어 MemoryVoucherRepository 내부에 Map을 멤버변수로 선언할 때 선언과 동시에 초기화해야하는지, 생성자를 통해 초기화해야할지 고밉입니다. 현재는 불변을 위해 final을 사용한다는 점에서 선언과 동시에 초기화를 수행합니다.
   
2. Entity 클래스에 식별자 getter를 쓰면 안되는 것인가요? 데이터 저장만 하더라도 필드값을 꺼내욜 필요가 있는데, 식별자를 getter로 꺼내온다는 것 자체가 불안하긴 합니다.

3. repository에서 save 기능을 수행할 때 어떤 것을 반환해야할까?? 또한 저장이 안된다면 어떻게 즉시 판단할 수 있는 코드를 작성할까요?

4. commandLineApplication클래스의 메서드에 대한 테스트 코드를 수행하고 싶었으나, 메서드 내부의 입출력으로 Junit등의 테스트 도구로 테스트할 수 없었습니다. 구조를 잘못짠 것이겠죠?? 어떻게 접근해야할까요

3. 입출력 관련된 모든 정보를 Console 객체에 static 메서드로만 구현한다는 것이 불안합니다. 괜찮은 방법이 있을까요.
