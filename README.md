# w3-SpringBoot_Part_A

## 미션내용
- 고객 블랙 리스트 명단을 작성해 봅니다.
  - customer_blacklist.csv파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트 조회할 수 있습니다. (추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정할게요)
- YAML 프라퍼티를 만들고 어떤 설정을 만들 수 있을지 고민해보세요.
- 바우처를 메모리에서 관리하는 레포지토리는 개발 프로파일에서만 동작하게 해보세요.

## 구현내용

![Class Diagram D4](/img/orderproject_D4.png)

- 고객 블랙 리스트 명단을 작성해 봅니다.(추가할 필요는 없어요. 블랙리스트는 파일로만 관리된다고 가정할게요)
  - engine/io/BlackList.java csv파일 읽는 소스 및 파싱 소스 추가
  - CommandLineApplication.java 파싱 결과 출력 소스 추가

- YAML 프라퍼티를 만들고 어떤 설정을 만들 수 있을지 고민해보세요.
  - csv 파일 이름 설정(읽기 실패할 경우 소스에 하드코딩 되어있는 소스 읽음.)

- 바우처를 메모리에서 관리하는 레포지토리는 개발 프로파일에서만 동작하게 해보세요.
  - 아래 소스를 통해 프라퍼티 설정 
```java
// CommandLineApplication.java
var springApplication = new SpringApplication(CommandLineApplication.class);
    springApplication.setAdditionalProfiles("dev");
    var applicationContext = springApplication.run(args);
    var voucherProperties = applicationContext.getBean(VoucherProperties.class);
```
```java
// engine/voucher/repository/MemoryVoucherRepository.java
@Profile({"dev", "test"})
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
```

## PR포인트 & 궁금한점

- 이전 피드백을 대량 수정했습니다.
  - 파일 구조도 많이 달라지고 소스가 바뀌어 변화 history를 읽기 어려울 수도 있습니다. (추후 오답노트처럼 정리해보도록 하겠습니다.)
  - 그 전에 문의 주시면 소스공유하며 프레젠테이션 하겠습니다.

- **Spring도 처음이고 심지어 java 개발이 처음이기 때문에 아주 자잘한 팁이라도 과하게 많이 주시면 감사드리겠습니다!**
