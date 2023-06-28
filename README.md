# SpringBoot Basic Weekly Mission

## 📌 과제 설명 <!-- 어떤 걸 만들었는지 대략적으로 설명해주세요 -->
* `SpringbootApplication` : Main
* `Platform`              : 유틸 클래스
* `InvalidDataException`  : 예외 클래스
* `Message`               : 메시지 레코드 클래스
* `Menu`                  : 종료 / 생성 / 출력 메뉴 enum
* `ListMenu`              : 바우처 / 블랙고객 출력 메뉴 enum
* `VoucherType`           : 고정값 / 비율값 바우처 타입 enum
* `DiscountValue` : 할인값 입력 Dto
* `VoucherDto` : 바우처 Dto
* `VoucheMap` : 바우처 메모리 일급 컬렉션 클래스
* `InputConsole`          : 입력 클래스
* `OutputConsole`         : 출력 클래스
* `CustomerService`: 고객 서비스 클래스
* `CustomerRepository` : 고객 서비스 레포지토리 클래스
* `VoucherService`        : 바우처 서비스 클래스
* `VoucherRepository`     : 바우처 레포지토리 인터페이스
* `MemoryVoucherRepository`: 메모리 저장 바우처 레포지토리 클래스 (dev 프로퍼티)
* `FileVoucherRepository` : 파일 저장 바우처 레포지토리 클래스 (default 프로퍼티)
* `YamlProperty`          : Yaml 파일 속성 클래스
* `VoucherFactory`        : Voucher 클래스 생성 인터페이스
* `FixedVoucherFactory`   : 고정값 바우처 클래스 생성 클래스
* `PercentVoucherFactory` : 비율값 바우처 클래스 생성 클래스
* `Voucher`               : 바우처 인터페이스
* `FixedAmountVoucher`    : 고정값 바우처 클래스
* `PercentDiscountVoucher`: 비율값 바우처 클래스
* `CommandLineTemplate` : 사용자 입출력 템플릿 클래스
* `CreateVoucherTemplate` : 바우처 생성 템플릿 클래스
* `ListMenuTemplate` : 출력 메뉴 템플릿 클래스


## ✅ PR 포인트 & 궁금한 점 <!-- 리뷰어 분들이 집중적으로 보셨으면 하는 내용을 적어주세요 -->


* 테스트 코드 작성 시 IllegalStateError가 발생합니다
  * 특히 `CustomeRepositoryTest`, `FileVoucherRepository` 클래스처럼 `YamlProperties` 를 DI 받아야 하는 클래스의 경우 테스트 하는 방법을 찾기가 어려웠습니다.
  * 검색해보니 빈을 찾지 못하는게 이유라고 하는데 이 문제를 해결하는 노하우가 있다면 알고 싶습니다.
* 템플릿을 적용했습니다.
  * `Platform` 클래스에서 입출력 템플릿을 통해 각각 생성 템플릿, 출력 템플릿으로 연결되도록 수정했습니다.
  * 피드백 의도에 맞게 수정이 된건지 확인해주셨으면 좋겠습니다.



## 👩‍💻 요구 사항과 구현 내용 <!-- 기능을 Commit 별로 잘개 쪼개고, Commit 별로 설명해주세요 -->
- #### 기능 구현
- [x] Gradle 프로젝트 생성
  - [x] Spring Boot CLI로 생성
- [x] Command-Line-Application 입출력
  -> 서버 기능 없이 콘솔로 동작
  - [x] exit(0) 명령 입력
  - [x] create(1) 명령 입력
  - [x] list(2) 명령 입력
- [x] 종료
- [x] 생성
  - [x] 고정값 바우처 생성
  - [x] 비율값 바우처 생성
  - [x] 메모리 저장 (dev 프로퍼티일 때)
  - [x] 파일 저장 (default 프로퍼티일 때)
- [x] 조회
  - [x] 바우처 조회
  - [x] 블랙고객 조회
- [x] YAML 프로퍼티 적용
  - [x] 버전
  - [x] 바우처 조회 파일 경로
  - [x] 블랙고객 파일 경로
- [x] 로그
- [x] jar 파일 추출
  - [x] gradle 방식

## ✅ 피드백 반영사항  <!-- 지난 코드리뷰에서 고친 사항을 적어주세요. 재PR 시에만 사용해 주세요! (재PR 아닌 경우 삭제) -->
- @Autowired 사용 ✅
  - ‘필드 삽입은 권장되지 않는다’의 이유
  - bean 객체들을 주입할 때 현재는 권장되지 않는다.
  - 생성자 주입을 하는 필드의 후보를 선별하기 위해서 스프링 컨테이너가 필드의 상태를 체크한다. 이것에 대해 알아보자
  - 찾아본 거
    - 생성자 주입 방식의 더 큰 이점
      ◦ 초기화 시 필요한 모든 의존 관계 형성 → 안전성 확보
      ◦ 잘못된 패턴 찾기 가능 → 안전성 확보
      ◦ 테스트 쉬워짐 → 등록되지 않은 Bean에 의한 비정상 종료 방지
      ◦ 불변성 확보 → 멀티스레드 환경에서 thread-safety 보장
    - 필드 주입 방식의 단점
      ◦ 의존성 파악이 모호해지는 경향 → 순환참조 가능성 증가
      ◦ 스프링 IoC 컨테이너에서 빈을 일치시켜 충족할 수 있는 의존성이 가장 많은 생성자가 선택

      → 해당 과정에서 불필요한 오버헤드 발생.

- DTO 활용 ✅
  - 재료는 늘어나지만 파라미터는 늘어나지 않도록 하는 방법
  - DTO 를 사용하면 늘어난 파라미터를 하나로 정리해서 넘겨줄 수 있다.
    - 객체지향생활체조 준수 방법 중 하나
  - Spring MVC (Controller, Service, Repository 간 데이터 전달 과정에서 layer로써 유용)
  - DTO는 주로 Client ↔ Contoller ↔ Service 구간에서 자주 사용함
  - Record 클래스 혹은 Enum 클래스로 구현하면 좋다
  - data
    1. user input data(spring request parameter)
    2. entity → (db 혹은 persist layer)
    3. response, result
    - Service → response, entity
    - Controller → requeest, param
    - Repository → entity
- 커스텀 예외 클래스 ✅
  - throwable cause도 같이 넘겨주면 디버깅할 때 좋다.
    - 예외가 다른 예외를 발생시킬 수 있기 때문 `Chained Exception`
    - 큰 분류의 예외로 묶어서 다루기 위해서
    - 상속관계를 무시하고 사용하고 싶어서
      - 부모를 공유하는 두 자식 예외가 부모 예외를 기준으로 catch하게 된다면 정확한 원인을 알 수 없게됨
      - cause를 사용하므로써 상속관계를 무시하고 정확한 원인을 알 수 있다.
    - checked 예외를 unchecked 예외로 바꿀 수 있어서
      - 반드시 예외처리를 해줘야 하는 checked 예외를 상위 예외 클래스로 감싼다면 더이상 예외처리가 강제되지 않음
      - checked 예외가 unchecked 예외로 바뀌는 거임
  - 정적인 것(메시지)와 분리해줘야 한다.
    - 매번 예외 객체가 생성되기 때문
    - error message(정적 String 모음)와 exception 클래스의 분리 필요
- Script 식 코드 줄이기✅
  - Platform 클래스 Switch Case 내부 로직
  - 해야할 일을 순서대로 나열한 코드는 객체지향적이지 않다.
- 향상된 switch 문 ✅
  - 한 case 문에 여러 조건 가능 (쉼표로 구분)
  - break 문 필요없음
  - : 말고 → 사용
  - switch에서 값 리턴 가능
    - 값 리턴하는 경우에는 반드시 default 가 있어야 함
  - yield 키워드
    - 스위치 표현식/문장에서 값을 명시적으로 리턴하기 위한 키워드
    - 코드 블록 {} 내에서만 사용 가능
    - 코드 블록 내 계산된 값을 반환
    - return 키워드와 함께 쓸 수 없음 → level을 다르게 해서 써야함.return
      - return : switch 구문 내에서 값을 반환 후 switch 문 종료
      - yield: switch 구문 내에서 계산한 결과를 switch 표현식의 결과로 반환
- private 메소드로 뺀 건 좋은 시도✅
- template을 생각해보자. ✅
  - command line을 프린트하는 역할과 데이터 처리를 분리해서 생각해 볼 필요가 있다.
  - list menu template, createMenuTemplate , command line print이 따로 존재 & 파이프라인이 있다고 생각하고 짜도 좋을 것
- factory는 더 거시적으로 잘 보이도록!✅
  - enum이 하는 일과 using Enum이 하는 일을 생각해보자.  → 가독성, 유지보수성을 위해서
  - Using Enum 구간에서 Factory를 사용하면 더 쉽게 사용접근이 가능할 것
- 제네릭 (profile에 따라서 파일저장과 메모리저장)✅
  - standard Interface를 기준으로 구현
    - interface가 기준
    - 상속받은 클래스는 interface의 형태를 따라가야함
    - 이게 standard interface
  - <T> 이런 코드는 지양하는게 좋다고 함..
      - 왜인지는 모르겠음. 범용적이면 좋은거 아닌가? 타입 안정성이 필요한 것도 아닌데…
  - 찾아본거
    - 제너릭 한정자
      - <? extends T> : T 타입 포함한 하위타입인 제네릭
      - <? super T> : T 타입 포함한 상위타입인 제너릭
- 추상클래스 vs 인터페이스✅
  - 추상클래스 : 실체가 명확할때
    - 조금이라도 value를 가질 것 같으면 추상클래스
  - 인터페이스 : 추상의 끝판왕
- 주석, 로그는 필요한 곳에만✅
  - Ex) menu 입력시 에러 발생하는 경우
  - Exception에서 로그 작성하는 것이 아니라 예외 터진 후에 로그 메시지를 받아와서 작성하는 것이 좋다.
  - output 과 logger를 분리하려는 목적
- 단위테스트는 테스트 대상과 이름이 공통되는 편이 좋음✅

### Git Commit Convention
* feat : 기능
* fix  : 버그 수정
* docs : 문서 작업
* style: 포맷팅, ;추가
* refactor : 리팩토링 (기능 변경 X)
* test : 테스트 코드 추가
* chore : 유지 (빌드 작업, 패키지 메니저 작업)