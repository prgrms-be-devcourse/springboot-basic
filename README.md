# w3-SpringBoot_Part_A

## 2W2D MISSION

바우처 관리 시스템에 고객 적용하기

-[x] JDBC를 이용한 데이터 조회
-[ ] 바우처 관리 시스템에 고객을 조회할 수 있도록 기능 추가
-[ ] 바우처 테이블 정의

vouchers

voucher_id, UUID, primary key
type, enum
discountAmount, long
customer_id, FK

## 2W4D MISSION

바우처 지갑 만들기
-[ ] 특정 고객에게 voucher를 할당할 수 있다.
-[ ] 고객이 어떤 바우처를 보유하고 있는지 조회할 수 있다.
-[ ] 고객의 바우처를 삭제할 수 있다.
-[ ] 특정 바우처를 보유한 고객을 조회할 수 있다.

## 2W5D MISSION

-[ ] 트랜잭션 관리 추가
-[ ] 반복되는 코드가 보인다면 AOP를 적용할 수 있을지 고민