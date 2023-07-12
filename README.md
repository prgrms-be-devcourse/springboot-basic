# SpringBoot Basic Weekly Mission

### 프로젝트 설계도 및 클래스 역할/책임
![image](https://github.com/sujjangOvO/springboot-basic/assets/89267864/24c4370d-a996-4049-aed7-5b93c8536ad3)

**1차 리팩토링 이후 설계도**
![image](https://github.com/sujjangOvO/springboot-basic/assets/89267864/eb6ad9d8-eb98-4fb3-9647-08546f71b90b)


### 구현한 기능
- [x]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.
- [x]  바우처 관리 Command-line Application을  만들어본다.
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
        - this바우처 정보를 메모리에 관리한다. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. (나중에 영속성을 가지도록 변경할거에요 걱정마세요!)
    
- [x]  적절한 로그를 기록하고 `logback` 설정을 해서 에러는 파일로 기록된다.
- [x]  실행가능한 `jar` 파일을 생성한다.


---
### Project History
- 데브코스 바우처 프로젝트로 코드리뷰를 받아 수정된 프로젝트입니다.
- Input, Output 인터페이스를 분리하고 구현한 Console 객체를 통해 입출력에 대한 부분에 대한 책임을 나누었습니다
- 예외 상황을 throw new로 처리하고 호출부분에서 예외를 처리합니다
- 메뉴 타입들을 열거형 enum으로 처리하였습니다
- CommandLineRunner를 구현하여 스프링 애플리케이션이 구동된 이후 실행되어야 하는 빈을 정의했습니다
- 매직넘버가 발생하는 부분을 수정하였습니다
- 인터페이스의 상위 타입을 선언하여 확장성을 고려하고 유지보수에 용이하도록 하였습니다
- 할인값 검증은 service에서 하지않고 생성자에서 검증하도록 하였습니다
- Consntant final class를 통해 메시지에 출력할 상수값을 모아두고 관리하도록 하였습니다
- domain 부분에서 출력 의존성을 가지면 안되므로 이 부분을 domain에 대한 정보를 인자로 넘기고 출력은 외부에서 진행하도록 변경 하였습니다
- try/catch문을 통해 코드레벨이 깊어지는 부분을 수정하기 위해 예외 처리가 필요한 부분에만 try/catch문을 적용하였습니다 따라서 추후 예외가 발생하는 부분을 찾기 쉬워집니다
