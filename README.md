# SpringBoot Basic Weekly Mission 2
> 바우처 관리 애플리케이션 만들기 2 - 김영주

# 1. 설계
## 프로그램 구조도

![image](https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/1b4f4d03-b56b-4c2a-9cf6-410db2bdffb7)



## 패키지 구조

```markdown
src/main/java/com.devcourse.voucherapp/

- controller/
  - VoucherController.java

- entity/
  - dto/
    - VoucherCreateRequestDto.java
    - VoucherUpdateRequestDto.java
    - VoucherResponseDto.java
    - VouchersResponseDto.java
  - voucher/
    - Voucher.java
    - FixDiscountVoucher.java
    - PercentDiscountVoucher.java
  - Menu.java
  - VoucherType.java
  - TriFunction.java
  
- exception/
  - MenuInputException.java
  - NotFoundVoucherException.java
  - DiscountAmountException.java
  - VoucherTypeInputException.java

- repository/
  - VoucherRepository.java
  - MemoryVoucherRepository.java
  - JdbcVoucherRepository.java

- service/
  - VoucherService.java

- view/
  - InputView.java
  - OutputView.java
  - ConsoleInputView.java
  - ConsoleOutputView.java
  - ViewManager.java

- CommandLineApplication.java
- VoucherappApplication.java
```



------

# 2. 컨벤션

## Git commit convention

> [Angular JS commit convention](https://velog.io/@outstandingboy/Git-커밋-메시지-규약-정리-the-AngularJS-commit-conventions)를 참고

```markdown
[커밋 메시지 헤더]
<type>(<scope>): <short summary>
  │       │             │
  │       │             └─⫸ 현재 시제로 작성한다. 마침표로 끝내지 않는다.
  │       │
  │       └─⫸ Commit Scope: 어느 부분을 수정했는지 작성한다.(생략 가능)
  │
  └─⫸ Commit Type: build|docs|feat|fix|refactor|test|style

[커밋 메시지 바디]
- 현재 시제로 작성한다.
- 변경 전과 후의 차이점과 이유에 대해 작성한다.
```

- `feat` : 새로운 기능 추가
- `fix` : 버그 수정
- `docs` : 문서 관련
- `style` : 스타일 변경 (포매팅 수정, 들여쓰기 추가, …)
- `refactor` : 코드 리팩토링
- `test` : 테스트 관련 코드
- `build` : 빌드 관련 파일 수정



------

# 3. 기능 구현 및 실행 화면

## 구현 사항

- [x]  H2 데이터베이스를 적용한다.
- [x]  프로필을 이용해 개발과 로컬 환경을 구분한다.
- [x]  할인권 수정 기능을 추가한다.
- [x]  할인권 삭제 기능을 추가한다.



## 실행 화면

> 할인권 수정, 삭제 기능 추가

```
[할인권 프로그램 v1.0]
1. 새 할인권 생성
2. 할인권 조회
3. 할인권 수정
4. 할인권 삭제
5. 프로그램 종료
입력 : 3

현재까지 생성된 할인권 목록입니다.
62f590c0-61c0-461d-90ce-ebafd031a4d8 | 고정 할인 | 1,000원
38de7fec-e170-49ee-b5bd-30d768a11fd5 | 비율 할인 | 20%

수정할 할인권의 ID를 입력하세요.
입력 : 62f590c0-61c0-461d-90ce-ebafd031a4d8

선택하신 할인권의 정보를 수정합니다.
62f590c0-61c0-461d-90ce-ebafd031a4d8 | 고정 할인 | 1,000원

고정 할인 금액을 입력하세요. (1이상의 자연수, 단위: 원)
입력 : 3000

할인권 수정이 완료되었습니다.
62f590c0-61c0-461d-90ce-ebafd031a4d8 | 고정 할인 | 3,000원

[할인권 프로그램 v1.0]
1. 새 할인권 생성
2. 할인권 조회
3. 할인권 수정
4. 할인권 삭제
5. 프로그램 종료
입력 : 4

현재까지 생성된 할인권 목록입니다.
62f590c0-61c0-461d-90ce-ebafd031a4d8 | 고정 할인 | 3,000원
38de7fec-e170-49ee-b5bd-30d768a11fd5 | 비율 할인 | 20%

삭제할 할인권의 ID를 입력하세요.
입력 : 62f590c0-61c0-461d-90ce-ebafd031a4d8

할인권이 정상적으로 삭제되었습니다.

[할인권 프로그램 v1.0]
1. 새 할인권 생성
2. 할인권 조회
3. 할인권 수정
4. 할인권 삭제
5. 프로그램 종료
입력 : 2

현재까지 생성된 할인권 목록입니다.
38de7fec-e170-49ee-b5bd-30d768a11fd5 | 비율 할인 | 20%
```
