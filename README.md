# SpringBoot Basic Weekly Mission 3
> 바우처 관리 애플리케이션 만들기 3 - 김영주

# 1. 프로젝트 개요

## 1-1. 설명
> 할인권과 고객 정보를 관리(생성, 조회, 수정, 삭제)하는 웹 애플리케이션 만들기 with Thymeleaf
1. **할인권(Voucher) 메뉴**
    - 기능
      - `생성` : 새 고정 금액 할인권 또는 퍼센트 비율 할인권 생성
      - `조회` : 전체 할인권 목록 조회
      - `수정` : 할인권의 고정 금액 또는 퍼센트 비율 수치 수정
      - `삭제` : 특정 할인권 삭제
    - 속성
      - `id` : UUID 기반의 할인권 고유 번호 
      - `type` : 할인 방식(고정 금액 or 퍼센트 비율)
      - `discountAmount` : 할인 금액 또는 비율
    - 방식
      - `고정 금액 할인(Fix Discount)`
        - 물건의 가격을 특정 고정 금액만큼 할인하는 방식
        - 1이상의 자연수, 단위 원
      - `퍼센트 비율 할인(Percent Discount)`
        - 물건의 가격의 일정 비율만큼 할인하는 방식
        - 1이상 100이하의 자연수, 단위 %

2. **고객(Customer) 메뉴**
   - 기능
     - `생성` : 새 일반 고객 생성
     - `조회` : 전체 고객 목록 조회, 블랙리스트 고객 목록 조회
     - `수정` : 고객 타입 수정 (일반 <-> 블랙리스트)
     - `삭제` : 특정 고객 삭제
   - 속성
     - `id` : UUID 기반의 고객 고유 번호
     - `type` : 고객 타입(일반 or 블랙리스트)
     - `nickname` : 고객 닉네임(중복 x)
   - 타입
     - `일반 고객`
     - `블랙리스트 고객`

<br>

## 1-2. 예외처리
1. **할인권(Voucher)**
   - 할인권 메뉴 목록에 없는 메뉴를 선택한 경우
   - 할인권 생성 시, 할인권 방식 목록에 없는 방식을 선택한 경우
   - 할인권 생성 또는 수정 시, 조건에 맞지 않는 금액 또는 비율을 입력한 경우
   - 할인권 수정 또는 삭제 시, 존재하지 않는 할인권 ID를 입력한 경우

2. **고객(Customer)**
   - 고객 메뉴 목록에 없는 메뉴를 선택한 경우
   - 고객 생성 시, 조건에 맞지 않는 닉네임을 입력한 경우
   - 고객 생성 시, 이미 있는 닉네임을 입력한 경우
   - 고객 수정 시, 고객 타입 목록에 없는 타입을 선택한 경우
   - 고객 수정 또는 삭제 시, 존재하지 않는 닉네임을 입력한 경우

------

# 2. 기능 구현 및 실행 화면

## 2-1. 요구사항
### 1주차
- [x]  메뉴 선택
- [x]  프로그램 종료
- [x]  고정 할인권 생성
- [x]  비율 할인권 생성
- [x]  전체 할인권 조회
- [x]  logback 이용한 로그 파일 생성
- [x]  실행 가능한 jar 파일 생성

### 2주차
- [x]  할인권 수정
- [x]  할인권 삭제
- [x]  H2 데이터베이스 적용
- [x]  프로필을 이용한 local, dev 환경 구분
- [x]  고객 정보 생성
- [x]  고객 정보 조회
    - [x]  전체 고객 목록 조회
    - [x]  상세 고객 정보 조회
    - [x]  블랙리스트 고객 목록 조회
- [x]  고객 정보 수정
- [x]  고객 정보 삭제

### 3주차
- [x]  Thymeleaf를 이용한 할인권 생성 (`/vouchers/create`)
- [x]  Thymeleaf를 이용한 할인권 조회 (`/vouchers`)
- [x]  Thymeleaf를 이용한 할인권 수정 (`/vouchers/{voucherId}/update`)
- [x]  Thymeleaf를 이용한 할인권 삭제 (`/vouchers/{voucherId}/delete`)
- [x]  Thymeleaf를 이용한 고객 생성 (`/customers/create`)
- [x]  Thymeleaf를 이용한 고객 조회
    - [x] 전체 고객 목록 조회 (`/customers`)
    - [x] 일반 고객 목록 조회 (`/customers/normal`)
    - [x] 블랙리스트 고객 목록 조회 (`/customers/black`)
- [x]  Thymeleaf를 이용한 고객 수정 (`/customers/{customerNickname}/update`)
- [x]  Thymeleaf를 이용한 고객 삭제 (`/customers/{customerNickname}/delete`)

<br>

## 2-2. 실행 화면

### 할인권(Voucher)
> 새 할인권 생성

<img width="1438" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/440ccedc-58de-428b-8345-b5e6ede2a509">

<br>
<br>

> 전체 할인권 조회

<img width="1424" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/a392effd-e998-423c-ba58-21ea7fdbd4c1">

<br>
<br>

> 할인권 수정

<img width="1435" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/d5382e8d-dc1b-48f2-b543-6dbc460b4990">

<br>
<br>

> 할인권 삭제

<img width="1437" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/2f86e26d-2dbb-40f4-a094-6c13c261cba7">
<img width="1424" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/8162b645-a17e-494d-bd44-bbdc650b13ed">

<br>
<br>


### 고객(Customer)
> 새 고객 생성

<img width="1435" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/01ffc5d3-531a-4450-8bcd-6c940fb41e68">

<br>
<br>

> 전체 고객 조회

<img width="1433" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/e26ff728-4832-4c37-9848-229e04e5935d">

<br>
<br>

> 일반 고객 조회

<img width="1435" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/7d485e95-db27-4515-a1d9-21185d113a51">

<br>
<br>

> 블랙리스트 고객 조회

<img width="1423" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/81003add-aeed-47ab-bb17-d0e10898d10f">

<br>
<br>

> 고객 수정

<img width="1433" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/bf319193-5227-4ff3-81bc-2f364331b910">

<br>
<br>

> 고객 삭제

<img width="1431" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/a4eb8b5a-6983-4eda-9158-cb1d5bf1a270">
<img width="1426" alt="image" src="https://github.com/prgrms-be-devcourse/springboot-basic/assets/49775540/c2ab6980-adde-43a8-a834-3b6cc159b456">

------

# 4. Git commit convention

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