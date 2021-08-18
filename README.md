# w3-SpringBoot_Part_A

## 🚀 2일차 미션

---

### 📌 미션 요구사항

#### [1W2D]

바우처 관리 Command-line Application을 만들어본다.
(https://dzone.com/articles/interactive-console-applications-in-java)

- CommandLineApplication 클래스를 작성한다.
- AnnotationConfigApplicationContext 를 이용해서 IoC 컨테이너를 생성하고 서비스, 레 포지토리를 빈으로 등록한다.
- 프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.

```
=== Voucher Program ===
Type exit to exit the program.
Type create to create a new voucher.
Type list to list all vouchers.
```

- create / list 커맨드를 지원한다.
    - create 커맨드를 통해 바우처를 생성할수 있습니다. (FixedAmountVoucher, PercentDiscountVoucher)
    - list 커맨드를 통해 만들어진 바우처를 조회할 수 있습니다.
  

#### [1W3D]
- 컴포넌트 스캔을 통해 빈이 등록되게 해보세요.

- 메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
  - 기존 메모리 레포지토리는 지우지 말아주세요.

### 📝 기능 목록

#### [1W2D]

- 컨테이너를 생성하고 서비스, 레포지토리를 빈으로 등록한다.
- 지원 가능한 명령어 안내를 출력한다.
- exit를 입력받아 프로그램을 종료한다.
- create를 입력받아 바우처를 생성한다.
    - Fixed, Percent 중 어떤 바우처인지 선택하고 할인 금액 또는 할인율을 입력받아 생성한다.
- list를 입력받아 현재 바우처를 출력한다.
- 지원 가능한 명령어가 아닐 경우 에러 메세지를 출력한다.

#### [1W3D]

- 컴포넌트 스캔으로 빈을 등록한다.
- 바우처를 입력받아 파일에 저장한다. ㅍ
- 파일을 조회하여 바우처 리스트를 출력한다.
- 아이디가 중복되는지 확인한다.

### ⚙ 개발 환경

```
java 16, 
maven 3.8.2
springboot 2.5.3
```

### ▶ 실행 방법

```
Mission1W2D 클래스의 main 메소드 실행 
```

### 📄 실행 결과

**명령어 안내**

```
=== Voucher Program ===
Type exit to exit the program.
Type create to create a new voucher.
Type list to list all vouchers.
```

**create**

```
create
생성하고 싶은 바우처를 선택하고 할인양 또는 할인율을 입력해주세요. (쉼표 ',' 를 사용하여 구분해주세요)
1. Fixed Amount Voucher
2. PercentDiscount Voucher
ex) 입력 예시 1, 1000 또는 2, 20
1, 100
성공적으로 등록되었습니다. 다음 명령을 입력해주세요.

create
생성하고 싶은 바우처를 선택하고 할인양 또는 할인율을 입력해주세요. (쉼표 ',' 를 사용하여 구분해주세요)
1. Fixed Amount Voucher
2. PercentDiscount Voucher
ex) 입력 예시 1, 1000 또는 2, 20
2, 20
성공적으로 등록되었습니다. 다음 명령을 입력해주세요.

create
생성하고 싶은 바우처를 선택하고 할인양 또는 할인율을 입력해주세요. (쉼표 ',' 를 사용하여 구분해주세요)
1. Fixed Amount Voucher
2. PercentDiscount Voucher
ex) 입력 예시 1, 1000 또는 2, 20
1, 700

성공적으로 등록되었습니다. 다음 명령을 입력해주세요.
```

**list**

```
list
PercentDiscountVoucher {voucherId = af962569-db47-49e2-85e9-9534134a1e7c, percent = 20}
FixedAmountVoucher {voucherId = 3fe89cd0-2974-4930-820e-acf1a765ffba, amount = 100}
FixedAmountVoucher {voucherId = 6959f49a-1cc9-40bf-8fd3-bdabd6baea08, amount = 700}
```

**exit**

```
프로그램 종료
```