# SpringBoot Basic Weekly Mission

[노션에서 미션 확인가기](https://www.notion.so/backend-devcourse/Part1-3-38f57acca0dd490db11393701417943a)

## **1차 PR 수정 사항**

</br>

- [x] try - catch 반복 수정
- [x] enum 장점 살려서 refactor
- [x] client 의존성 문제 refactor
- [x] Message.java를 public static으로 변경
- [x] PercentVoucher.java amount 자료형(long -> int) 및 예외처리 먼저 한 후 Id 할당 받는 것 수정
- [x] domain 패키지로 관련 바우처와 멤버 이동

</br>

## **과제 요구 사항**

- 바우처 관리 애플리케이션
  - [x] 단위 테스트 작성
  - [x] customers 테이블 정의 및 추가
  - [x] customerRepository 구현
  - [x] voucher db 관리
- 바우처 지갑
  - [x] 특정 고객에게 바우처 할당
  - [x] 고객이 어떤 바우처를 보유하고 있는지 조회
  - [x] 고객이 보유한 바우처 제거
  - [x] 특정 바우처를 보유한 고객을 조회

</br>

## **PR 궁금한 포인트**

- Controller나 Service 등에서 자주 처리하는 로직들, 또 어떻게 테스트하는지가 궁금합니다...
  - 이유 : 구현하면서 각 클래스의 역할을 모호하게 한 건지..
  - 정확히 몰라서 그런건지.. 그 탓에 서비스부터는 테스트를 어떻게 해야할지 감이 안왔습니다...
- 지금 현재 voucher_wallets에 대한 접근을 CustomerRepository와 VoucherRepository가 접근하고 있습니다.
  - 접근보다도 각 함수나 불러오는 과정에서 일관성을 해치는 것같아서 이부분에 대한 조언이 듣고싶습니다.
  - (CustomerJdbcRepository 클래스 위 주석에 애매한 함수를 명시해두었습니다!)
