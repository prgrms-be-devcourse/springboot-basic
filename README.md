
# **SpringBoot Basic Weekly Mission 2**


## 🔖 소감

<hr>

## **📌 과제 설명**

### **흐름도**

![](https://velog.velcdn.com/images/onetuks/post/9f71d1c4-f526-4fa6-b131-db61a9102b22/image.png)


### **클래스 다이어그램**

![](https://velog.velcdn.com/images/onetuks/post/0e2837f5-b1c7-43aa-bf9a-1ace3e8e794f/image.png)


## **✅ PR 포인트 & 궁금한 점**

wallet 기능 구현에 대해서 봐주셨으면 좋겠습니다.

- voucher 테이블에 customer 외래키를 추가했습니다.
- voucher 도메인 기존 기능에서 외래키에 대한 crud를 추가하면 된다고 생각해 voucher 도메인을 확장했습니다.
- 테이블 간 관계를 최대한 단순하게 갖는것이 좋을 것이라고 생각해 이렇게 구현했는데, 의도에 맞게 구현됐는지 궁금합니다.

## **👩‍💻 요구 사항과 구현 내용**

- [x]  테스트
- [x]  Customer
- [x]  Voucher
- [x]  Wallet

## **✅ 피드백 반영사항**

- 유효성 검사
  - Null 방어 + 메소드로 분리
- 정적 팩토리 메소드 알고쓰기
  - private 생성자 사용하기
- 레포지토리 패턴
  - 영속성 변경에 안정성 부여
- var 알고쓰기
  - 지양할 곳
    - 테스트코드
    - 프론트엔드가 볼 수 있는 컨트롤러
    - 핵심 비즈니스 로직
    - 여러 군데에서 활용하는 범용 클래스
- 민감정보 감추기
  - jasypt
- 테스트
  - 컨벤션
  - FIRST 속성
  - @JdbcTest
- SQL
  - 키워드 지양

---

# 📮 1차 피드백

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
