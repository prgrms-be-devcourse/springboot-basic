# SpringBoot Basic Weekly Mission 1
### 과제 요구사항
- [x] [Commandline Application](https://dzone.com/articles/interactive-console-applications-in-java)
- [x]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 사용해서 프로젝트를 만든다.
- [x]  스프링부트 애플리케이션으로 만든다. 
- [x]  메모리 관리가 아닌 파일로 관리가 되는 Repository 사용하고 
- [x] 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
- [x]  YAML 프라퍼티를 만들어 사용한다.
- [x]  적절한 로그를 기록하고 `logback` 설정을해서 에러는 파일로 기록된다.
- [x]  실행가능한 `jar` 파일을 생성한다.

### **바우처 관리 애플리케이션 설명**
    === Voucher Program ===
    Type exit to exit the program.
    Type create to create a new voucher.
    Type list to list all vouchers.
    Type blacklist to list all blacklist custom info

- create 커맨드를 통해 바우처를 생성할수 있다. (FixedAmountVoucher, PercentDiscountVoucher 중 선택)
    ```
    >>create
    Type voucher type(fixed or percent) and amount
    fixed
    1000
    ```
- list 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
    ```
    >>list
    id: af4f2e6d-c39d-4ea3-b114-955c79103d78
    type: fixed
    amount: 1000.0
    ```

- blacklist 커맨드를 통해 csv파일의 기록된 블랙리스트 정보를 조회할 수 있다.
    ```
    >>blacklist
    name: moon age: 25 sex: man description: X
    name: moon age: 26 sex: woman description: X
    ```
### Class Diagram

![Untitled-2022-03-29-1415](https://user-images.githubusercontent.com/37391733/163552243-7c7b42fb-3fb1-49b1-b612-9cce565bef8b.png)
