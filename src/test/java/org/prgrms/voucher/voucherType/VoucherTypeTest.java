package org.prgrms.voucher.voucherType;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.exception.NoSuchVoucherTypeException;
import org.prgrms.voucher.discountType.Discount;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;

public class VoucherTypeTest {

  @DisplayName("of메소드는 1번을 입력받았을 때 FixedAmountVoucher 를 리턴한다.")
  @Test
  void isFixedType() {
    //given
    String fixed = "1";
    //when
    VoucherType voucherType = VoucherType.of(fixed);
    //then
    assertEquals(VoucherType.FIXED, voucherType);
  }

  @DisplayName("of메소드는 2번을 입력받았을 때 PercentDiscountVoucher 를 리턴한다.")
  @Test
  void isPercentType() {
    //given
    String percent = "2";
    //when
    VoucherType voucherType = VoucherType.of(percent);
    //then
    assertEquals(VoucherType.PERCENT, voucherType);
  }

  @DisplayName("of메소드는 선택지에 없는 번호를 입력받으면 NoSuchVoucherTypeException을 던진다")
  @Test
  void isNoMatchingType() {
    //given
    String noType = "3";
    //when&then
    assertThrows(NoSuchVoucherTypeException.class, () -> VoucherType.of(noType),
        noType + "은 존재하지 않습니다. 다시입력해주세요");

  }

  @DisplayName("of메소드는 숫자가 아닌 값을 입력받으면 NoSuchVoucherTypeException을 던진다")
  @Test
  void nonNumericType() {
    //given
    String nonNumeric = "가abc*";
    //when&then
    assertThrows(NoSuchVoucherTypeException.class, () -> VoucherType.of(nonNumeric),
        nonNumeric + "은 존재하지 않습니다. 다시입력해주세요");

  }

  @DisplayName("generateVoucher 메소드는 fixed바우처 타입과, discount를 입력받으면 FixedAmountVoucher타입을 리턴한다")
  @Test
  void generateFixedVoucher() {
    //given
    VoucherType fixed = VoucherType.FIXED;
    Discount discount = new DiscountAmount("3000");
    //when
    Voucher voucher = VoucherType.generateVoucher(fixed, discount);
    //then
    assertInstanceOf(FixedAmountVoucher.class, voucher);
  }

  @DisplayName("generateVoucher 메소드는 percent바우처 타입과, discount를 입력받으면 PercentDiscountVoucher타입을 리턴한다")
  @Test
  void generatePercentVoucher() {
    //given
    VoucherType percent = VoucherType.PERCENT;
    Discount discount = new DiscountRate("10");
    //when
    Voucher voucher = VoucherType.generateVoucher(percent, discount);
    //then
    assertInstanceOf(PercentDiscountVoucher.class, voucher);
  }

  @DisplayName("generateDiscount 메소드는 fixed바우처 타입과 할인금액을 입력받으면 DiscountAmount타입을 리턴한다")
  @Test
  void generateFixedDiscount() {
    //given
    VoucherType fixed = VoucherType.FIXED;
    String value = "2000";
    //when
    Discount discount = VoucherType.generateDiscount(fixed, value);
    //then
    assertInstanceOf(DiscountAmount.class, discount);
  }

  @DisplayName("generateDiscount 메소드는 percent바우처 타입과 할인금액을 입력받으면 DiscountRate타입을 리턴한다")
  @Test
  void generatePercentDiscount() {
    //given
    VoucherType percent = VoucherType.PERCENT;
    String value = "50";
    //when
    Discount discount = VoucherType.generateDiscount(percent, value);
    //then
    assertInstanceOf(DiscountRate.class, discount);
  }


}
