# w3-SpringBoot_Part_A

### 21_08_17
- 구현 사항
  - 2일차 요구사항의 구현을 중심으로 진행했습니다.
    - CommandLineApplication 클래스를 작성한다
    - 프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다
```
=== Voucher Program ===
Type exit to exit the program
Type create to create a new voucher
Type list to list all vouchers
```

    - Create / list 커멘드를 지원한다.
        - Create 커멘드를 통해 Voucher를 생성할 수 있습니다
        - list 커멘드를 통해 만들어진 바우처를 조회할수 있습니다.

### 21_08_18
- 구현 사항
  - 입출력 관련 부분을 별도의 인터페이스로 분리했습니다(io 패키지 아래)