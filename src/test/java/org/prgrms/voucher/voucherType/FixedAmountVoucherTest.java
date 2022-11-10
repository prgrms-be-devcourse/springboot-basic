package org.prgrms.voucher.voucherType;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.exception.PaymentCannotBeNegativeException;
import org.prgrms.voucher.discountType.DiscountAmount;

class FixedAmountVoucherTest {

  @DisplayName("주어진 금액만큼 할인한 가격을 리턴한다")
  @Test
  void testDiscount() {
    //given
    String givenAmount = "100";
    FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),
        new DiscountAmount(givenAmount));
    //when
    long beforeDiscount = 1000;
    long discounted = fixedAmountVoucher.discount(beforeDiscount);

    //then
    assertEquals(900, discounted);
  }

  @DisplayName("할인 된 금액이 마이너스 일때 PaymentCannotBeNegativeException을 던진다")
  @Test
  void testWithMinus() {
    //given
    FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),
        new DiscountAmount("1000"));
    //when&then
    assertThrows(PaymentCannotBeNegativeException.class,() -> fixedAmountVoucher.discount(500L));
  }
}