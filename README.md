# SpringBoot Basic Weekly Mission

[노션에서 미션 확인가기](https://www.notion.so/backend-devcourse/Part1-3-38f57acca0dd490db11393701417943a)

</br>

## diagram

|                                         간략한 다이어그램                                         |
| :-----------------------------------------------------------------------------------------------: |
| ![class diagram](https://github.com/jki503/springboot-basic/blob/main/class_diagram.png?raw=true) |

</br>

## package

- part1
  - configuragtion
    - AppConfiguration.java
  - domain
    - Member.java
    - MemberDto.java : csv에서 읽어올 객체
    - Voucher : 인터페이스
    - FixedAmountVoucher
    - PercentAmountVoucher
    - VoucherType : 입출력에서 생성할 경우 판별 할 enum 타입
  - exception
    - member
      - BlackListEmptyException.java : 블랙리스트 멤버 없을 경우
    - voucher
      - FixedAmountException.java : 고정금액 에러
      - PercentErrorException.java : 퍼센트 에러
      - VoucherListEmptyException.java : 바우처 없을 경우
      - VoucherTypeMissingException : 바우처 타입 잘못 입력할 경우
      - CommandTypeMissingException : 명령어가 없을 경우
  - io
    - Console.java : Client.java에서 사용할 입출력 기구
    - Input, Output : 콘솔에서 사용할 인터페이스
    - Message.java : 프롬포트에서 사용할 메시지 클래스
  - member
    - repository
      - MemberRepository.java : 제네릭 타입으로 구현한 인터페이스
      - FileMemberRepository.java : csv Read 레포지토리
    - MemberController : ui 계층과 맞닿아 전달만 하는 계층
    - MemberService : 비즈니스 로직 처리 계층
  - order.voucher
    - repository
      - MemoryVoucherRepository
      - VoucherRepository
    - VoucherController
    - VoucherRepository
  - ui
    - client : ui 계층 담당
    - CommandType : 유저가 입력할 명령어 enum 타입

</br>

## **PR 수정 사항**

</br>

- [x] try - catch 반복 수정
- [x] enum 장점 살려서 refactor
- [x] client 의존성 문제 refactor
- [x] Message.java를 public static으로 변경
- [x] PercentVoucher.java amount 자료형 및 예외처리 수정
- [x] domain 패키지로 관련 바우처와 멤버 이동

## **TODO**

> Generic의 장점을 사용해서 다시 refactor 해보기  
> AOP 적용해서 log 및 Property 세팅은
> PR approve 받고 현재 진행중인 과제에 적용해서 리뷰 받아도 될까요...?
