# SpringBoot Basic Weekly Mission


## 요구사항

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
- [x]  적절한 로그를 기록하고 `logback` 설정을해서 에러는 파일로 기록된다.
- [x]  실행가능한 `jar` 파일을 생성한다.



## 프로젝트 그림그리기 및 소개하기

#### 최초
![image](/doc/diagram.png)


#### 현재
![image](/doc/바우처설계도_w1.png)

