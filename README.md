# SpringBoot Basic Weekly Mission

## 📌 과제 설명 <!-- 어떤 걸 만들었는지 대략적으로 설명해주세요 -->
* `SpringbootApplication` : Main
* `Platform`              : 유틸 클래스
* `InvalidDataException`  : 예외 클래스
* `Menu`                  : 종료 / 생성 / 출력 메뉴 enum
* `ListMenu`              : 바우처 / 블랙고객 출력 메뉴 enum
* `VoucherType`           : 고정값 / 비율값 바우처 타입 enum
* `InputConsole`          : 입력 클래스
* `OutputConsole`         : 출력 클래스
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


## ✅ PR 포인트 & 궁금한 점 <!-- 리뷰어 분들이 집중적으로 보셨으면 하는 내용을 적어주세요 -->



### Git Commit Convention
* feat : 기능
* fix  : 버그 수정
* docs : 문서 작업
* style: 포맷팅, ;추가
* refactor : 리팩토링 (기능 변경 X)
* test : 테스트 코드 추가
* chore : 유지 (빌드 작업, 패키지 메니저 작업)