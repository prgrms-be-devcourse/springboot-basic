## 📌 과제 설명
스프링부트 `basic` 위클리미션을 코드리뷰하는 저장소입니다.

### 개발 환경
- Springboot 3.1.0
- Java 17
- Gradle(Build Tool)

## 👩‍💻 요구 사항과 구현 내용

#### 기능명세와 PullRequest
- 기능명세는 docs..에 정리해두었습니다!
- 1, 2주차 PR 내용은 docs..에 정리해두었습니다!

#### 추가된 기능
- 바우처의 `Rest Controller`만 따로 구현을 해놓고 그에 따른 테스트를 진행하였습니다.
- 요구사항에 맞는 `View` 페이지와 그에 맞는 `Spring MVC Controller`를 구현했습니다.
- 예외에 대한 핸들링을 `@RestControllerAdvice`와 `@ExceptionHandler`를 사용했습니다.

## ✅ PR 포인트 & 궁금한 점
#### PR 포인트
- 프론트와 원활한 소통을 위해 예외 핸들링 부분에서 `DTO`에 대한 `MethodArgumentNotValidException` 예외 응답 양식에 대해 따로 구현을 해놓았습니다.
  - `ResponseError` 클래스에서 따로 구현을 해놓았습니다.
  - 구현 방식은 `record`를 사용했고 `@JsonInclude` 어노테이션을 활용해 필요한 값만 넘기도록 하였습니다.

#### 궁금한 점
- `toDto`와 `toEntity`와 같은 매핑 메소드는 누구에게 책임을 부여해야하는 것인지 궁금합니다!
  - 현재 저는 자주 변하지 않는 것이 자주 변하는 곳에 의존하지 않도록 하였습니다.
  - 즉, `DTO`는 자주 변하기 때문에, `toDto`를 `Entity`가 아닌 `DTO` 클래스에 선언하였습니다.
  - 그리고 `toEntity`는 `Entity` 자신으로 변환하는 것이기 때문에 `Entity` 내부에 선언했습니다.
  - 아니면 가독성을 위해 `mapper package`를 만들어 한 곳에서 관리하는 것이 좋을까요?
