# Week 3 - SpringBoot Part 1

## INDEX

- [[W3D2] Mission : Command-Line Application](#3w2d-mission)
- [[W3D3] Mission : Component Scan & File Repository](#3w3d-mission)
- [[W3D4] Mission : 고객 블랙리스트 명단 작성](#3w4d-mission)
- [[W3D5] Mission : Logging & SpringBoot Migration](#3w5d-mission)
  <br/>

## [W3D2] Mission

바우처 관리 Command-line Application을 만들어본다. [[참고 링크]](https://dzone.com/articles/interactive-console-applications-in-java)

- `CommandLineApplication` 클래스를 작성한다.
- `AnnotationConfigApplicationContext` 를 이용해서 `IoC Container`를 생성하고 `Service`, `Repository`를 빈으로 등록한다.
- 프로그램이 시작하면 다음과 같이 지원 가능한 명령어를 알려준다.
  ```
  === Voucher Program ===
  Type exit to exit the program.
  Type create to create a new voucher.
  Type list to list all vouchers.
  ```

- `create` / `list` 커맨드를 지원한다.
    - `create` 커맨드를 통해 바우처를 생성할 수 있다. (`FixedAmountVoucher`, `PercentDiscountVoucher`)
    - `list` 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
- 바우처를 메모리에서 관리하도록 설계한다. 다른 미션에서 영속성을 가지도록 변경한다.
  <br/>

## [W3D3] Mission

- `Component Scan`을 통해서 Bean이 등록되게 한다.

- 메모리 관리가 아닌 **파일로 관리되는 Repository**를 만들어본다.
    - 기존 메모리 Repository는 지우지 않는다.
      <br/>

## [W3D4] Mission

고객 블랙리스트 명단을 작성해본다.

- `customer_blacklist.csv`파일을 만들고 Spring Application에서 해당 파일을 읽을 수 있고 블랙리스트를 조회할 수 있도록 설계한다.  
  (추가할 필요는 없고, 블랙리스트는 파일로만 관리된다고 가정한다.)

- `YAML properties`를 만들고 어떤 설정을 만들 수 있을지 고민해 본다.
- 바우처를 메모리에서 관리하는 Repository는 **개발 profile에서만 동작**하게 한다.
  <br/>

## [W3D5] Mission

- 적절한 Log를 기록하고 **LogBack** 설정을 해서 에러는 파일로 기록되어야 한다.
- `SpringBoot Application`으로 변경한다.
- 실행가능한 `jar`파일을 생성한다.

___
