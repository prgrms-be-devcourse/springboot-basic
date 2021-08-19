# w3-SpringBoot_Part_A

## 미션내용
바우처 관리 Command-line Application을 만들어본다. ([https://dzone.com/articles/interactive-console-applications-in-java](https://dzone.com/articles/interactive-console-applications-in-java))

- CommandLineApplication 클래스를 작성한다.
- AnnotationConfigApplicationContext 를 이용해서 IoC 컨테이너를 생성하고 서비스, 레포지토리를 빈으로 등록한다.
- 프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.

```bash
=== Voucher Program ===
Type **exit** to exit the program.
Type **create** to create a new voucher.
Type **list** to list all vouchers.
```
- create / list 커맨드를 지원한다.
    - create 커맨드를 통해 바우처를 생성할수 있습니다. (FixedAmountVoucher, PercentDiscountVoucher)
    - list 커맨드를 통해 만들어진 바우처를 조회할 수 있습니다.
- 바우처를 매모리에 관리해세요. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. → 나중에 영속성을 가지도록 변경할거에요 걱정마세요!

## 구현내용

![Class Diagram](/img/orderproject.png)

- create/list 커맨드 지원
  - Console을 이용해 입력받고 CommandLine 클래스에서 조건처리한다.
  - VoucherService 클래스에서 Voucher를 생성한다.(어떤것을 생성할지는 구현하지 않음 - 사용자에게 커맨드 입력받아 선택생성하면 된다고 생각)
  - MemoryVoucherRepository 클래스에서 모든 데이터를 Map.Entry를 이용하여 넘겨받는다.
- 바우처를 메모리에 관리
  - MemoryVoucherRepository 클래스에서 Map을 이용하여 저장

## PR포인트 & 궁금한점

- Repository 에서 데이터를 가져와서 Console 에서 list를 출력하는 과정이 적절한것인지 궁금합니다.
  - Entry로 가져오는 방법이 적절한가(데이터가 안전하지 않아보임)
  - Console에서 처리하는 과정이 적절한가
- VoucherService 클래스의 createVoucher 함수에서 input 값이 적절한지 체크하는 과정을 넣었는데 더 단순하게 구현가능한지 혹은 분리할 수 있는 부분이 있는지 궁금합니다.

- **Spring도 처음이고 심지어 java 개발이 처음이기 때문에 아주 자잘한 팁이라도 과하게 많이 주시면 감사드리겠습니다!**
