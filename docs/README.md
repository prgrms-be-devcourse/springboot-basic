# 기능 명세서

## 입력

### 요구사항
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



## 기능

### 1. 입력
- [x] 입력 인터페이스 설정에 따라 입력하는 기능 추가
  - `BufferedReader`, `Scanner`, `Console`
- [ ] 매 입력마다 실행 구문 추가

#### 예외
- [x] `exit`, `create`, `list`입력에 대한 예외 추가
- [x] 메뉴외의 값 입력시 예외 추가
