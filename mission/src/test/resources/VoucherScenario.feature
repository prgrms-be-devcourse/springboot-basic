Feature: 바우처 생성 기능을 확인한다.
  Background:
    Given 유저는 생성 메뉴를 선택했다.

    Scenario: 바우처 생성에 실패한다. 유저는 다시 메뉴를 선택한다.
      When 해당하지 않는 바우처 타입을 선택했다.
      Then 에러메시지와 함께 유저는 다시 메뉴를 선택한다.

    Scenario: 유저는  FixedAmountVoucher생성에 실패한다.
      When 유저는 amount에 음수를 입력한다.
      Then 에러메시지와 함께 유저는 다시 메뉴를 선택한다.

    Scenario: 바우처 생성에 성공한다. 유저는 FixedAmountVoucher를 생성한다.
      When 유저는 음수가 아닌 값을 넘겨준다.
      Then amount의 값이 유효하다면 해당 바우처를 생성한다.

    Scenario: 유저는 PercentDiscountVoucher생성에 실패한다.
        When 유저는 percent에 100을 넘는값을 입력한다.
        Then 에러메시지와 함께 유저는 다시 메뉴를 선택한다.

    Scenario: 유저는 PercentDiscountVoucher생성에 실패한다.
        When 유저는 percent에 0이하의 값을 입력한다.
        Then 에러메시지와 함꼐 유저는 다시 메뉴를 선택한다.

    Scenario: 유저는 PercentDiscountVoucehr 생성에 성공한다.
          When 유저는 pecent에 1~100의 값을 입력한다.
          Then PercentDiscountVoucher가 생성된다.


