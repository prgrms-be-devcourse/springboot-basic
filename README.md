# SpringBoot Basic Weekly Mission
스프링부트 basic 위클리미션을 코드리뷰하는 Repository입니다.

주차별 과제는 노션에서 확인하세요!
[노션에서 미션 확인가기](https://www.notion.so/backend-devcourse/Part1-3-38f57acca0dd490db11393701417943a)

## 📌 과제 설명 <!-- 어떤 걸 만들었는지 대략적으로 설명해주세요 -->
바우처 관리 애플리케이션 Week1
- [x]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.
- [x]  바우처 관리 Command-line Application을 만들어본다.
    - 참고: [https://dzone.com/articles/interactive-console-applications-in-java](https://dzone.com/articles/interactive-console-applications-in-java)
    - [x]  스프링부트 애플리케이션으로 만든다. (Web기능이 없이만듭니다. 즉, 서버가 띄지 않고 커맨드라인 애플리케이션으로 동작해야한다.)
    - [x]  프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.

   ```bash
   === Voucher Program ===
   Type exit to exit the program.
   Type create to create a new voucher.
   Type list to list all vouchers.
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
## 👩‍💻 요구 사항과 구현 내용 <!-- 기능을 Commit 별로 잘개 쪼개고, Commit 별로 설명해주세요 -->
## command
#### Command
입력 메뉴를 검증하는 Enum 클래스입니다.
#### commandService
바우처 관리 프로그램을 실행하는 클래스입니다.
#### Input, Output
입력과 출력을 담당하는 클래스입니다.
## voucher
#### VoucherType
고정할인과 비율할인 두 가지가 있습니다.
할인 종류에 따라 다른 검증 단계를 가집니다.
#### FixedAmountVoucher, PercentAmountVoucher
#### VoucherService
데이터 저장과 조회를 담당하는 메소드들이 있습니다.
#### MemoryVoucherRepository, FileVoucherRepository
yml 파일의 active 속성에 따라 다른 repository가 선택됩니다.
## customer
#### Customer, CustomerType
블랙리스트 조회를 위한 클래스입니다.
#### FileCustomerRepository
csv 파일을 읽어 조회하는 역할을 합니다.
#### CustomerService
블랙리스트 조회를 맡습니다.
## resources
#### customer_blacklist.csv
블랙 리스트 조회를 위한 csv 파일
#### logFile.log
warn 이상의 에러 로그를 기록하기 위한 파일입니다.
#### logback.xml
로그 설정을 위한 xml 파일입니다.
#### voucher.csv
voucher 리스트를 조회하기 위한 csv 파일입니다.
## ✅ 피드백 반영사항  <!-- 지난 코드리뷰에서 고친 사항을 적어주세요. 재PR 시에만 사용해 주세요! (재PR 아닌 경우 삭제) -->

## ✅ PR 포인트 & 궁금한 점 <!-- 리뷰어 분들이 집중적으로 보셨으면 하는 내용을 적어주세요 -->
1. 에러 로그 처리할때 commandService 한 곳에서 처리를 하게 두었습니다. 로그에 에러 발생 지점 클래스를 알려주기 위해 아래 사진처럼 (Command.class 부분) 지저분한 코드가 여러 곳에서 생기게 되었습니다. 에러 처리는 한 곳에서 하되 깨끗한 코드를 얻을 수 있는 방법이 있을까요?
   ![스크린샷 2022-04-11 00 23 51](https://user-images.githubusercontent.com/58693617/162626782-c7553780-dc7b-421c-b504-a4f199b3e466.png)
2. VoucherType Enum 클래스에서 올바른 숫자 할인 금액or율 범위(ex: 할인율 0~100)에 대한 검증을 하고 있는데 따로 검증 클래스를 만들어 검증하는 편이 더 나은 방법이었을까요?