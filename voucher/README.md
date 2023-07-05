
# SpringBoot Part1 Weekly Mission 
## 바우처 관리 어플리케이션

- [x]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.
- [ ]  바우처 관리 Command-line Application을  만들어본다.
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
        - this바우처 정보를 매모리에 관리한다. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. (나중에 영속성을 가지도록 변경할거에요 걱정마세요!)
- [ ]  적절한 로그를 기록하고 `logback` 설정을 해서 에러는 파일로 기록된다.
- [ ]  실행가능한 `jar` 파일을 생성한다.

(심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능

- [ ]  메모리 관리가 아닌  파일로 관리가 되는 Repository를 한번 만들어보세요.
    - 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
- [ ]  고객 블랙 리스트 명단을 작성한다.
- customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수있다 (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정합니다.)
- [ ]  YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해본다.

## 기능목록

### Currency
- [x] Currency의 확장성을 위해서 정적 팩토리 메서드 도입 -> 확장성 및 가독성을 위해
- [x] 객체 생성 될 때 입력 값이 0보다 작은 경우 예외 발생

- [x] 나중에 할인 고정 금액 및 비율 입력값 정수 범위 벗어나도 (소수 되어도) 되게 수정하기

## Voucher
- [x] 고정이든 비율이든 할인 되는 퍼센트나 금액이 음수이거나 올바르지 못한경우 예외 처리
  - [x] percent discount에서 discountRate가 0<~<=100의 값이 아닌 다른 값이면 예외 발생 (음수 포함)
- [x] fixed 에서 원래 금액보다 할인하는 금액이 더 큰 경우 예외 발생
- [x] 할인 금액이 int로 나눠 떨어지지 않는다면 소수점 자리는 버림 해서 할인한다.
- [x] Voucher는 생성시점과 만료 시점을 상태값으로 가지고 있어야 한다 -> 7월 5일 수정


## Controller
- [ ] view를 통해서 명령어를 입력받는다.
  - [ ] EXIT -> 프로그램 종료한다
  - [ ] LIST -> service에서 list 가져와 view를 통해 각각 출력한다.
  - [ ] CREATE -> 생성하는데, 입력을 받아야 한다.
    - 고정 OR 퍼센트 선택 입력 받기
- [ ] DTO 생성
  - [ ] VoucherRequest -> controller에서 Request로 dto로 감싸서 service로 넘기기
    - Request는 voucher 타입, 할인 금액 / 할인율, 생성할 수량을 받는다 (voucherType, discountValue, amount)
  - [ ] Voucher Response -> id 값, voucherType, voucher 할인율 보여주기 
    - Response는 voucher의 고유 식별자, voucher 타입, 할인 금액/ 할인율을 보여준다 (id, voucherType, discountValue)
