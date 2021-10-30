# w3-SpringBoot_Part_A

## 미션내용
1. 적절한 로그를 기록하고 로그백설정을 해서 에러는 파일로 기록되야 합니다.
2. SpringBoot 애플리케이션으로 변경합니다.(SpringApplication를 사용해주세요)
3. 실행가능한 jar 파일을 생성합니다.

## 구현내용

![Class Diagram D5 - D4와 동일](/img/orderproject_D4.png)

- 적절한 로그를 기록하고 로그백설정을 해서 에러는 파일로 기록되야 합니다.
  - 파일과 터미널에 error 와 warning이 같이 기록될 수 있도록 warn 으로 설정(warn 로그가 설정되어있지 않음.)
  - logger 추가(단, logback.xml 설정을 info로 바꿔야함.)

- SpringBoot 애플리케이션으로 변경합니다.(SpringApplication를 사용해주세요)
  - CommandLineApplication.java 소스 아래와 같이 수정

```java
// Spring Application
var springApplication = new SpringApplication(CommandLineApplication.class);
springApplication.setAdditionalProfiles("dev");
var applicationContext = springApplication.run(args);
var voucherProperties = applicationContext.getBean(VoucherProperties.class);
```

- 실행가능한 jar 파일을 생성합니다.
  - 아래 명령어로 jar 생성 후 파일 바깥으로 이동(.gitignore 회피위해)
  - order-0.0.1-SNAPSHOT.jar 파일

```bash
mvn clean package spring-boot:repackage
mv target/order-0.0.1-SNAPSHOT.jar .
```

## PR포인트 & 궁금한점

- out 디렉토리 안의 jar 파일을 사용하지 않고(ERROR 발생하며 실행이 되지 않음) target에 생성된 jar를 사용하는 이유가 궁금합니다.
  - out 폴더의 jar 파일과 target 폴더의 jar 파일 차이가 궁금합니다.

- **Spring도 처음이고 심지어 java 개발이 처음이기 때문에 아주 자잘한 팁이라도 과하게 많이 주시면 감사드리겠습니다!**
