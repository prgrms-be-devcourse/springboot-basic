# SpringBoot Basic Weekly Mission

스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

주차별 과제는 노션에서 확인하세요!
[노션에서 미션 확인가기](https://www.notion.so/backend-devcourse/Part1-3-38f57acca0dd490db11393701417943a)


## SpringBoot Part1 Weekly Mission

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

(**심화**) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능

- [x]  메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.  
        - 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
- [x]  고객 블랙 리스트 명단을 작성한다.  
        - customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수있다 (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정합니다.)
- [x]  YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해본다.


### 🧑‍💻 요구 사항과 구현 내용


##### Console
* 입력, 출력 기능을 담당합니다.
##### VoucherService
* 명령어를 입력받아 각 명령어에 맞는 기능을 수행하도록 합니다.
* 입력값이 create인 경우
    * **local**인 경우 memoryVoucherRespository에 입력한 voucher 정보를 저장합니다.
    * **dev**인 경우 fileVoucherRespository을 통해 voucherDB.csv에 입력한 voucher 정보를 저장합니다.
* 입력값이 list인 경우
    * **local**인 경우 memoryVoucherRepository에 저장된 voucher 정보를 출력합니다.
    * **dev**인 경우 fileVoucherRepository에 의해 voucherDB.csv에 저장된 voucher 정보를 출력합니다.
* 입력값이 black-list인 경우
    * 고객 블랙리스트 명단을 출력합니다.
* 입력값이 exit인 경우
  * 프로그램을 종료합니다.
* 다른 입력값이 들어온 경우 다시 입력을 받도록 합니다.
##### CustomerService
* 고객 리스트 관리를 합니다.
##### Repository
* voucher 정보와 고객 리스트 정보를 관리합니다.

### 💡 PR 포인트 & 궁금한 점

---
##### 1. 로그 관련
로그를 사용하는 목적이  
1. 서비스 동작 상태 파악
2. 장애 파악 & 알람  
  
으로 생각하고 있는데 (구글 서치를 해서도 이렇게 나오더라구요!) 어느상황에서 사용해야하는지 감이 잘 안 잡혀서 질문드립니다!  
예를들어, 어떤 기능을 동작하는 상태를 보기 위해 구현하는데 사용하는 메소드의 모든 곳에 로그를 찍어 확인을 해야하는지 특정 메서드에만 찍어도 되는지 모르겠습니다.
또, 로그레벨을 어떤 것을 어느 상황에 사용해야 하는지에 대해서도 에프님만의 팁이나 방법이 있다면 무엇일까요?!?
##### 2. 
원래는 VoucherManager라는 클래스를 따로 만들어 콘솔에서 voucherService클래스를 사용하지 않고   
VoucherManager에서 VoucherService를 사용해 저장을 하거나 리스트를 보여주는 작업을 처리했습니다.  
그런데 굳이 VoucherManager라는 클래스를 만들어 사용하는 것 같아 콘솔에서 VoucherService를 직접 사용해 기능을 구현하도록 코드를 수정하였습니다.  
콘솔은 입출력만 하는 기능을 담당하고 VoucherManager 같은 클래스를 따로 만들어 기능을 처리해주는 작업을 해야하는지 아니면 콘솔에서 그런 기능을 처리해도 되는지 궁금합니다!

##### 3. 예외처리
제가 예외를 만들어서 예외처리를 해주었는데 제가 잘 사용한 것인지 어떤 식으로 사용을 해야했었는지 궁금합니다.
예외를 커스텀하는 과정에서 잘못되거나 고쳐야하는 부분이 있으면 알려주셨으면 합니다!  
예외를 만들어야 하는 경우에는 무엇이 있을까요??


