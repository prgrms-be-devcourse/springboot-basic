# w3-SpringBoot_Part_A

## 🚀 2일차 미션

---

### 📌 미션 요구사항

바우처 관리 Command-line Application을 만들어본다.
(https://dzone.com/articles/interactive-console-applications-in-java)
- CommandLineApplication 클래스를 작성한다.
- AnnotationConfigApplicationContext 를 이용해서 IoC 컨테이너를 생성하고 서비스, 레
포지토리를 빈으로 등록한다.
- 프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.

```
=== Voucher Program ===
Type exit to exit the program.
Type create to create a new voucher.
Type list to list all vouchers.
```

- create / list 커맨드를 지원한다.  
  - create 커맨드를 통해 바우처를 생성할수 있습니다. (FixedAmountVoucher,
PercentDiscountVoucher)
  - list 커맨드를 통해 만들어진 바우처를 조회할 수 있습니다.
    
### 📝 기능 목록

- 컨테이너를 생성하고 서비스, 레포지토리를 빈으로 등록한다.
- 지원 가능한 명령어 안내를 출력한다.
- exit를 입력받아 프로그램을 종료한다.
- create를 입력받아 바우처를 생성한다.
  - Fixed, Percent 중 어떤 바우처인지 선택하고 할인 금액 또는 할인율을 입력받아 생성한다.
- list를 입력받아 현재 바우처를 출력한다.
- 지원 가능한 명령어가 아닐 경우 에러 메세지를 출력한다.