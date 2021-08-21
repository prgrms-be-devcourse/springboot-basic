# w3-SpringBoot_Part_A

## 개요

- Run Application
- [3W.2D] Misson : Command-Line Application 만들기
- [3W.3D] Mission : Component Scan & File Repository

## Run Application

- `org.prgrms.kdt.CommandAppTest` Class 의 `main` method 실행

## [3W.2D] Misson

바우처 관리 Command-line Application을 만들어본다.

### Detail

- [x] `CommandLineApplication` 클래스를 작성한다.
- [x] `AnnotationConfigApplicationContext` 를 이용해서 `IoC 컨테이너`를 생성하고 서비스, `레포지토리`를 빈으로 등록한다.
- [x] 프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.
  ```
  === Voucher Program ===
  Type exit to exit the program.
  Type create to create a new voucher.
  Type list to list all vouchers.
  ```
- [x]`create` / `list` 커맨드를 지원한다.
- [x] `create` 커맨드를 통해 바우처를 생성할수 있습니다. (`FixedAmountVoucher`,
      `PercentDiscountVoucher`)
- [x] `list` 커맨드를 통해 만들어진 바우처를 조회할 수 있습니다.
- [x] 바우처를 **매모리**에 관리해세요. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. → 나중에 영속성을 가지도록 변경할거에요 걱정마세요!

## [3W.3D] Mission : Component Scan & File Repository

Component Scan & File Repository 구현

### Detail

- [x] 컴포넌트 스캔을 통해서 빈이 등록되게 해보세요.
- [x] 메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
  - 기존 메모리 레포지토리는 지우지 말아주세요.
  - file path : @(project root dir)/voucher/voucher.csv
  - file type (csv)
  ```csv
  PercentDiscountVoucher,7d3ec388-2c7a-4e8f-b91d-4b34bbb3873b,30
  FixedAmountVoucher,0280aece-8ca1-479e-8dd2-80bcf282ce06,5000
  PercentDiscountVoucher,d56aaf89-edce-499f-9c22-9af57b6a70a0,10
  PercentDiscountVoucher,bcbd0b69-243a-4c80-a6da-afa5662b010b,7
  FixedAmountVoucher,c9b4d499-00ef-488f-aa94-2456af66afe6,500
  ```
