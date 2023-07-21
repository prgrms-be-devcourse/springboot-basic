
# **SpringBoot Basic Weekly Mission 2**


## 🔖 소감

<hr>

## **📌 과제 설명**

### **흐름도**
![흐름도.png](%ED%9D%90%EB%A6%84%EB%8F%84.png)

### **클래스 다이어그램**
![클래스다이어그램.png](%ED%81%B4%EB%9E%98%EC%8A%A4%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png)

## **✅ PR 포인트 & 궁금한 점**

- nullable 컬럼
  - 테이블에 null 값이 가능한 컬럼은 도메인 클래스에서 어떻게 관리하는지 궁금합니다.
  - 생각해본 해법입니다.
    1. 로직에서는 Optinal로 처리, 컨트롤러 단에서 Optional.get() 으로 직렬화
    2. null 상태를 가리키는 무의미한 인스턴스 객체를 생성해서 사용

## **👩‍💻 요구 사항과 구현 내용**

- [x]  테스트
- [x]  Customer
- [x]  Voucher
- [x]  Wallet

## **✅ 피드백 반영사항**

### 📮 1차 피드백

<details>
<summary>유효성 검사</summary>
<div markdown="1">

- Null 방어

```java
if (name == null || name.isBlank()) {
    throw new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText());
}
```

- 유효 조건을 메소드로 분리

```java
public static CommandMenu getCommandMenu(String menuString) {
    return Arrays.stream(CommandMenu.values())
              .filter(commandMenu -> isMatched(menuString, commandMenu))
              .findAny()
              .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_MENU.getMessageText()));
}

private static boolean isMatched(String menuString, CommandMenu commandMenu) {
    boolean isMatchedName = Objects.equals(menuString, commandMenu.name());
    boolean isMatchedOrdinal = Objects.equals(menuString, String.valueOf(commandMenu.ordinal()));
    return isMatchedName || isMatchedOrdinal;
}
```

</div>
</details>

<details>
<summary>정적 팩토리 메소드 알고쓰기</summary>
<div markdown="1">

- 장점
  - 객체 생성 관리 이점 → 팩토리 메소드를 통해 쉽게 객체 생성 가능
  - 대신 생성자는 private 이어야 함
  - 간단한 메소드 이름
  - 구현부분에 대한 정보은닉
- 단점
  - 상속 통한 기능확장 불가
  - static 키워드 자체의 응집도 이슈
  - 범용 클래스인 경우 private 생성자 사용하기

</div>
</details>

<details>
<summary>레포지토리 패턴</summary>
<div markdown="1">

- 영속성 변경에 안정성 부여
- 쉽게 말해서 repository를 인터페이스로 구현해서 사용

</div>
</details>


<details>
<summary>var 알고쓰기</summary>
<div markdown="1">

- 지양할 곳 (내 의견임)
  - 테스트코드
  - 프론트엔드가 볼 수 있는 컨트롤러
  - 핵심 비즈니스 로직
  - 여러 군데에서 활용하는 범용 클래스

</div>
</details>


<details>
<summary>민감정보 감추기</summary>
<div markdown="1">

- jasypt 모듈
- build 종속성

  ```java
  implementation 'com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4'
  ```

- 설정 클래스

```java
@Configuration
public class JasyptConfiguration {

    @Value("${jasypt.encryptor.algorithm}")
    private String algorithm;

    @Value("${jasypt.encryptor.pool-size}")
    private int poolSize;

    @Value("${jasypt.encryptor.string-output-type}")
    private String stringOutputType;

    @Value("${jasypt.encryptor.key-obtention-iterations}")
    private int keyObtentionIterations;

    @Bean
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig configuration = new SimpleStringPBEConfig();
        configuration.setAlgorithm(algorithm);
        configuration.setPoolSize(poolSize);
        configuration.setStringOutputType(stringOutputType);
        configuration.setKeyObtentionIterations(keyObtentionIterations);
        configuration.setPassword(getJasyptEncryptorPassword());
        encryptor.setConfig(configuration);
        return encryptor;
    }

    private String getJasyptEncryptorPassword() {
        try {
            ClassPathResource resource = new ClassPathResource("src/main/resources/jasypt-encryptor-password.txt");
            return String.join("", Files.readAllLines(Paths.get(resource.getPath())));
        } catch (IOException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_FILE_ACCESS.getMessageText(), e.getCause());
        }
  }
}
```

- 테스트 클래스

```java
class JasyptConfigurationTest {

    @Test
    void jasypt() {
        String url = "jdbc:mysql://localhost:/";
        String username = "";
        String password = "!";

        String encryptUrl = jasyptEncrypt(url);
        String encryptUsername = jasyptEncrypt(username);
        String encryptPassword = jasyptEncrypt(password);

        System.out.println("encrypt url : " + encryptUrl);
        System.out.println("encrypt username: " + encryptUsername);
        System.out.println("encrypt password: " + encryptPassword);

        assertThat(url).isEqualTo(jasyptDecrypt(encryptUrl));
    }

    private String jasyptEncrypt(String input) {
        String key = "!";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.encrypt(input);
    }

    private String jasyptDecrypt(String input) {
        String key = "!";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }

}
```

- yaml 파일

```java
jasypt:
 encryptor:
   algorithm: PBEWithMD5AndDES
   bean: jasyptStringEncryptor
   pool-size: 2
   string-output-type: base64
   key-obtention-iterations: 100
spring:
 datasource:
   url: ENC(암호화된 url 스트링)
   username: ENC(암호화된 유저이름)
   password: ENC(암호화된 패스워드)
   driver-class-name: com.mysql.cj.jdbc.Driver
```

</div>
</details>


<details>
<summary>테스트</summary>
<div markdown="1">

- 컨벤션
  - given - when - then
- FIRST 속성
  - [좋은 테스트, FIRST 속성](https://velog.io/@onetuks/%EC%A2%8B%EC%9D%80%ED%85%8C%EC%8A%A4%ED%8A%B8-FIRST-%EC%86%8D%EC%84%B1)
- @JdbcTest
  - Jdbc 관련된 빈 만 컴포넌트 스캔 → DataSourse 같은거 주입해줌
  - 대신 내가 만든 컴포넌트는 주입 안 해줌 → Import 로 따로 해줘야 함

</div>
</details>


<details>
<summary>SQL</summary>
<div markdown="1">

- all(*) 키워드 지양
  - 원하는 컬럼을 직접 지정해서 얻어오셈
- count 함수는 필요한 경우만, 아니면 where 조건이 있는 경우만

</div>
</details>

### 📮 2차 피드백
<details>
<summary>확장을 고려한 구조 선택</summary>
<div markdown="1">

- 등록과 수정에 같은 dto 사용 -> 추후 더 필요할 것으로 보임
  - CreateRequest, UpdateRequest 등
- Customer 상태를 boolean으로 판단
  - 추후 상태가 늘어날 것을 대비해 enum으로 관리하면 좋음
</div>
</details>

<details>
<summary>기본 자료형을 감싸는 원시값 포장 권고</summary>
<div markdown="1">

- 기본 자료형을 그대로 사용하려고 하지 말고, 프로그램을 클래스의 모음으로 구성하면 유지보수 용이
- String name; -> Name name;
</div>
</details>

<details>
<summary>@ConfigurationProperties</summary>
<div markdown="1">

- 필드가 많은 설정 클래스에서는 @Value 보다는 @ConfigurationProperties

```java
@Configuration
@ConfigurationProperties("jasypt.encryptor")
@EnableEncryptableProperties
public class JasyptConfiguration {

    private String algorithm;
    private int poolSize;
    private String stringOutputType;
    private int keyObtentionIterations;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig configuration = new SimpleStringPBEConfig();
        configuration.setAlgorithm(algorithm);
        configuration.setPoolSize(poolSize);
        configuration.setStringOutputType(stringOutputType);
        configuration.setKeyObtentionIterations(keyObtentionIterations);
        configuration.setPassword(getJasyptEncryptorPassword());
        encryptor.setConfig(configuration);
        return encryptor;
    }

    private String getJasyptEncryptorPassword() {
        try {
            ClassPathResource resource = new ClassPathResource("src/main/resources/jasypt-encryptor-password.txt");
            return String.join("", Files.readAllLines(Paths.get(resource.getPath())));
        } catch (IOException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_FILE_ACCESS.getMessageText(), e.getCause());
        }
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public String getStringOutputType() {
        return stringOutputType;
    }

    public int getKeyObtentionIterations() {
        return keyObtentionIterations;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public void setStringOutputType(String stringOutputType) {
        this.stringOutputType = stringOutputType;
    }

    public void setKeyObtentionIterations(int keyObtentionIterations) {
        this.keyObtentionIterations = keyObtentionIterations;
    }
}
```
</div>
</details>

<details>
<summary>테스트</summary>
<div markdown="1">

- 메소드 호출 테스트는 verify
  - verify() 메소드로 특정 메소드가 호출되었는지를 증명(테스트)
  - void 메소드 테스트에 찰떡! 우와!
- 테스트 코드에 추가 로직 금지!
  - 조건문 같은 암튼 로직은 다 안 됨.
  - 필요하다면 테스트 메소드를 쪼개자
</div>
</details>

<details>
<summary>Optional 도메인 필드는 ㄴㄴㄴ</summary>
<div markdown="1">

- Optional은 직렬화 안 됨.
  - 필드로 쓰면 안 됨.
</div>
</details>

<details>
<summary>내장 DB 스키마 설정</summary>
<div markdown="1">

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:test;MODE=MySQL
    driver-class-name: org.h2.Driver
    username: test
    password: test1234!
  h2:
    console.enabled: true
  sql:
    init:
      mode: always
      schema-locations: classpath:schema/schema.sql
```
</div>
</details>

### 📮 3차 피드백

<details>
<summary>ERD 수정</summary>
<div markdown="1">

- 기존 ERD
  - vouchers(voucher_id, voucher_type, discount_value, created_at, customer_id)
  - customers(customer_id, name, black)
  - 이렇게 둘 만 있고, vouchers 에 fk(customer_id) 로 지갑 기능을 구현
- 변경 ERD
  - vouchers(voucher_id, voucher_type, discount_value, created_at)
  - customers(customer_id, name, black)
  - wallets(wallet_id, voucher_id, customer_id)
  - 이렇게 새로 지갑 테이블을 만들어서 확장 대비
</div>
</details>

<details>
<summary>테스트</summary>
<div markdown="1">

- 테스트 코드에서만 사용하는 함수는 어떻게 처리할까?
  - 로직 코드에 넣어두지 말고, 테스트 클래스 내에서 해당 기능 클래스를 새로 생성
  - 빈을 주입받는 방식으로 실행
</div>
</details>