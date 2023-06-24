# 기능 명세서


## [요구사항]

---

### 1.입력
> CLI (Command linke inteface)한 환경에서 실행되도록 한다.

[ 프로그램 실행 구문 ]
```
=== Voucher Program ===
Type exit to exit the program.
Type create to create a new voucher.
Type list to list all vouchers.
```

### 입력 예외 추가 기능
- 입력시 `양쪽 공백`은 `삭제`
- `대소문자 상관없이` 동일한 메뉴 이름이면 사용가능

### 2. 바우처
> `create`와 `list` 커맨드를 통해 바우처를 생성 및 조회할 수 있다.

- `create`: 바우처 생성
  - `FixedAmountVoucher` or `PercentDiscountVoucher`
- `list`: 바우처 조회
  1. 바우처 정보를 메모리 관리
  2. (심화), 파일로 관리가 되는 Repository
     - 개발 프로파일에서만 동작
- `블랙 리스트`: 고객 명단 블랙 리스트 작성
  - `customer_blacklist.csv` 파일
  - 파일을 읽을 수 있고 리스트 조회 가능


### 3. 기타 요구사항
- `YAML`프러퍼티를 만들고 어떤 설정을 만들 수 있을지 
- 실행가능한 `jar`파일을 생성
- `logback`을 통한 에러 파일로 기록


<br>

## 기능

---

### 1. 입력
- [x] 입력 인터페이스 설정에 따라 입력하는 기능 추가
  - `BufferedReader`, `Scanner`, `Console`
- [x] 매 입력마다 실행 구문 추가

#### 예외
- [x] `exit`, `create`, `list`입력에 대한 예외 추가
- [x] 메뉴외의 값 입력시 예외 추가

### 2. 바우처
- [ ] 바우처 생성하는 기능
- [ ] 바우처 조회 기능
