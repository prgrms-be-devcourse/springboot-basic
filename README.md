## 요구사항

- SpringBoot Part1 Weekly Mission

    (기본) **바우처 관리 애플리케이션**

    - [x]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.
    - [x]  바우처 관리 Command-line Application을 만들어본다.
        - 참고: [https://dzone.com/articles/interactive-console-applications-in-java](https://dzone.com/articles/interactive-console-applications-in-java)
        - [x]  스프링부트 애플리케이션으로 만든다. (Web기능이 없이만듭니다. 즉, 서버가 띄지 않고 커맨드라인 애플리케이션으로 동작해야한다.)
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

    (심화) 파일을 통한 데이터관리 기능과 고객 블랙 리스트 명단 관리기능

    - [ ]  메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
        - 기존 메모리 레포지토리는 지우지 말고 개발 프로파일에서만 동작하게 해보세요.
    - [ ]  고객 블랙 리스트 명단을 작성한다.
    - customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수있다 (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정합니다.)
    - [x]  YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해본다.



- 현재 심화 요구사항 일부는 개발하지 못 했습니다. 추가적으로 개발할 예정입니다.

----------

## 프로젝트 설명

### 애플리케이션 실행 흐름

- `VoucherManagementApplication`에서 `Input`으로부터 입력값을 받아 `Dispatcher`로 넘긴다.
- `Dispatcher`는 지원되는 커맨드 목록을 확인하여, `Input`값에 해당하는 `Command`를 처리할 수 있는 `Controller`에 보낸다.
- `Controller`에서는 `Command`에 따른 작업을 필요에 따라 `VoucherService`를 호출하여 수행한다.
  - `VoucherService`는 작업 중 생성된 데이터를 `VoucherRepository`에 기록한다.
- `Controller`는 업무를 수행한 후, 해당하는 `view`의 이름과 `view` 랜더링에 필요한 정보를 결합하여 `ModelAndView`로 `Dispatcher`에 반환한다.
- `Dispatcher`는 `Drawer`에게 `ModelAndView`를 보내서 화면을 그리게 한다.



### 예외처리

- 개별 클래스들에서 발생한 예외를, `Dispatcher` 에서 받아, 오류 페이지로 리다이렉트하게끔 정보를 돌려 보낸다.
  - 별도로, 예외 발생시 로그도 출력시킨다.
- 오류 페이지에 들어가는 정보는 `profile` 설정에 따라 달라진다:
  - `dev` 프로파일의 경우 예외 정보를 세부적으로 받는다. (예외 클래스, 예외 메시지를 전달한다.)
  - `prod` 프로파일의 경우 간소화된 예외 정보를 전달받는다.



### 뷰 출력

- `resources`의 `console-template` 경로의 `txt`파일들을 소스로 한다.
  - `txt` 파일 내의 `${프로퍼티명}`형태의 식을 `Drawer`가 읽어서, 거기에 해당하는 값을 `Model`에서 받아 동적으로 출력한다.



### 환경설정

- `application.yaml` 기반으로 바우처의 유효한 할인 값의 범위를 정한다.
- `dev`와 `prod`프로파일에 따라 오류 정보 출력이 달라진다.
- 로그 설정의 경우, `dev`환경은 콘솔에 출력, `prod`환경은 파일로 기록한다.

--------

## 기타

- 테스트 코드가 아직 많이 부족한 상황이라 지속적으로 추가하도록 하겠습니다.
- 콘솔 컨트롤러 구조를 만들다 보니 지나치게 많은 `flag` 변수들이 존재하게 되는 것 같은데 , 어떤 식으로 관리하면 좋을지가 궁금합니다.
  - 예를 들어, 리다이렉트해야 한다거나, 입력을 기다려야 한다거나 등
- `Voucher` 종류들에 대한 정보를 `VoucherType` `enum`으로 관리하고 있고, 여기에 뷰와 관련된 로직을 넣고 있는데, 이런 식의 분리가 적절한지 궁금합니다.