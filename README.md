# w3-SpringBoot_Part_A

## Mission W3D2

- [x] `CommandLineApplication` 클래스를 작성한다.
- [x] `AnnotationConfigApplicationContext` 를 이용해서 `IoC 컨테이너`를 생성하고 서비스, `레포지토리`를 빈으로 등록한다.
- [x] 프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.
  ```
  === Voucher Program ===
  Type exit to exit the program.
  Type create to create a new voucher.
  Type list to list all vouchers.
  ```
- [x] `create` / `list` 커맨드를 지원한다.
- [x] `create` 커맨드를 통해 바우처를 생성할수 있습니다. (`FixedAmountVoucher`,
  `PercentDiscountVoucher`)
- [x] `list` 커맨드를 통해 만들어진 바우처를 조회할 수 있습니다.
- [x] 바우처를 **매모리**에 관리해세요. 어플리케이션이 종료가 되어 데이터가 모두 사라져도 괜찮습니다. → 나중에 영속성을 가지도록 변경할거에요 걱정마세요!

### 구현 내용

- [x] Factory Pattern 을 통한 Voucher 인스턴스를 생성
- [x] Strategy Pattern 을 통한 명령어 분기 처리

### ❓ 질문사항

- x

<hr>

## Mission W3D3

- [x] 컴포넌트 스캔을 통해서 빈이 등록되게 해보세요.
- [x] 메모리 관리가 아닌 파일로 관리가 되는 Repository를 한번 만들어보세요.
    - 기존 메모리 레포지토리는 지우지 말아주세요.

### ❓ 질문사항

- x

<hr>

## Mission W3D4

- [x] 고객 블랙 리스트 명단을 작성해 봅니다.
    - `customer_blacklist.csv` 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수 있습니다. (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고
      가정할게요)
- [x] YAML 프라퍼티를 만들고 어떤 설정을 만들수 있을지 고민해보세요.
    - 개발 환경, Test 서버 환경, 배포 환경과 같은 설정들을 분리하여 만들 수 있을 것 같다.
- [x] 바우처를 메모리에서 관리하는 레포지토리는 개발 프로파일에서만 동작하게 해보세요.

### ❓ 질문사항

- x

<hr>

## Mission W3D5

- [x] 적절한 로그를 기록하고 로그백설정을해서 에러는 파일로 기록되야 합니다.
- [x] SpringBoot 애플리케이션으로 변경합니다. (SpringApplication 를 사용해주세요)
- [x] 실행가능한 jar 파일을 생성합니다.

```shell
mvn clean package spring-boot:repackage 
ll target 
java -jar target/dev-spring-order-0.0.1-SNAPSHOT.jar
```

### ❓ 질문사항

- [ ] AOP 레벨에서의 에러 핸들러 처리. java -jar target/dev-spring-order-0.0.1-SNAPSHOT.jar

<hr>

## Mission W4D1

- [ ] 바우처 관리 애플리케이션에 단위테스트를 작성해보세요.
    - 가능한 많은 단위 테스트코드를 작성하려고 노력해보세요.
    - 엣지 케이스(예외 케이스)를 고려해서 작성해주세요.
- [ ] `Hamcrest` 의 메쳐들을 다양하게 작성해보고 익숙해져 보세요.
- [ ] `AssertJ`도 한번 맛보세요.

### ❓ 질문사항

- [ ] 