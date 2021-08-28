# w3-SpringBoot_Part_A

### 21_08_17
- 구현 사항
  - 2일차 요구사항의 구현을 중심으로 진행했습니다.
    - CommandLineApplication 클래스를 작성한다
    - 프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다
    - Create / list 커멘드를 지원한다.
    - Create 커멘드를 통해 Voucher를 생성할 수 있습니다
    - list 커멘드를 통해 만들어진 바우처를 조회할수 있습니다.
```
=== Voucher Program ===
Type exit to exit the program
Type create to create a new voucher
Type list to list all vouchers
```

### 21_08_18
- 구현 사항
  - 입출력 관련 부분을 별도의 인터페이스로 분리했습니다(io 패키지 아래)
  - 3일차 요구사항을 구현했습니다
    - 컴포넌트 스캔을 통해서 빈이 등록되게 해보세요
    > FileVoucherRepository를 등록되게 했습니다.
    - 메모리 관리가 아닌 파일로 관리가 되는 Repository를 만들어 보세요.

- 피드백 중점사항
  - 예외처리가 정말 미숙합니다. 이 점 지적해주시면 감사하겠습니다.
  - InputStream, OutputStream을 올바르게 썻는지 지적해주시면 감사하겠습니다.

### 21_08_19
- 구현 사항
  - application.properties 파일을 이용하여 2개의 저장소가 다른 설정으로 동작하도록 했습니다.
    - active=dev 의 경우, MemoryVoucherRepo
    - active=prod 의 경우, FileVoucherRepo
  - blacklist 를 쓰고 읽을 수 있게 구현했습니다.
- 아직 못다한 것
  - Yaml 에 대해서 처음 배워 스프링에 Yaml 등록방식이 어색해 아직 공부중입니다.

### 21_08_22
- 피드백 구현사항
  - CLI App 에서 무거운 분기부분을 여럿 메서드로 나누었습니다.
  - File 저장 방식을 save 명령이 일어날때 마다 저장하는 것이 아닌, 메모리에 저장해놨다가 프로그램이 끝나면 파일에 저장되도록 변경됬습니다.
  - FixedAmount, PercentDiscount Voucher 객체 생성을 of 메서드를 통해서 일어나게 바꾸었습니다.
  - 분기의 조건을 enum을 이용하여 해보려했습니다.
    - enum 타입으로 선언시, 문자열 비교가 잘 되지 않아, static final String 키워드를 이용하여 비교했습니다
    - 예를들어 input이 "1"(String) 일 경우, 이를 enum Type과 비교하지 못하여 이렇게 바꾸었습니다. 이 점 지적해주시면 감사하겠습니다.
  - 파일과 service를 잇는 Provider는 기존의 File저장소에서 어떤 메서드를 부분화 시켜야 할 지 모르겠어서 아직 하지 못했습니다.
