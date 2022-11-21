package org.prgrms.voucher.voucherType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.exception.NoSuchVoucherTypeException;
import org.prgrms.voucher.discountType.Amount;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;

public class VoucherTypeTest {

  @DisplayName("콘솔에서 1번을 입력받았을 때 고정 타입을 리턴한다.")
  @Test
  void isFixedType() {
    //given
    int fixed = 1;
    //when
    VoucherType voucherType = VoucherType.of(fixed);
    //then
    assertEquals(VoucherType.FIXED, voucherType);
  }

  @DisplayName("콘솔에서 2번을 입력받았을 때 고정 타입을 리턴한다.")
  @Test
  void isPercentType() {
    //given
    int percent = 2;
    //when
    VoucherType voucherType = VoucherType.of(percent);
    //then
    assertEquals(VoucherType.PERCENT, voucherType);
  }

  @DisplayName("선택지에 없는 번호를 입력받으면 NoSuchVoucherTypeException을 던진다")
  @Test
  void isNoMatchingType() {
    //given
    int noType = 3;
    //when&then
    assertThrows(NoSuchVoucherTypeException.class, () -> VoucherType.of(noType),
        noType + "은 존재하지 않습니다. 다시입력해주세요");

  }

  @DisplayName("Voucher 선택시 숫자가 아닌 값을 입력받으면 NoSuchVoucherTypeException을 던진다")
  @Test
  void nonNumericType() {
    //given
    String nonNumeric = "가abc*";
    //when&then
    assertThrows(NoSuchVoucherTypeException.class, () -> VoucherType.of(nonNumeric),
        nonNumeric + "은 존재하지 않습니다. 다시입력해주세요");

  }

  @DisplayName("바우처 생성시 fixed바우처 타입을 입력받으면 FixedAmountVoucher타입을 리턴한다")
  @Test
  void generateFixedVoucher() {
    //given
    VoucherType fixed = VoucherType.FIXED;
    Amount discount = new DiscountAmount(3000L);
    //when
    Voucher voucher = fixed.generateVoucher(discount);
    //then
    assertInstanceOf(FixedAmountVoucher.class, voucher);
  }

  @DisplayName("바우처 생성시 percent바우처 타입을 입력받으면 PercentDiscountVoucher타입을 리턴한다")
  @Test
  void generatePercentVoucher() {
    //given
    VoucherType percent = VoucherType.PERCENT;
    Amount discount = new DiscountRate(10L);
    //when
    Voucher voucher = percent.generateVoucher(discount);
    //then
    assertInstanceOf(PercentDiscountVoucher.class, voucher);
  }

  @DisplayName("Amount 타입 생성시 fixed바우처 타입과 할인금액을 입력받으면 DiscountAmount타입을 리턴한다")
  @Test
  void generateFixedDiscount() {
    //given
    VoucherType fixed = VoucherType.FIXED;
    long value = 2000L;
    //when
    Amount discount = fixed.generateAmount(value);
    //then
    assertInstanceOf(DiscountAmount.class, discount);
  }

  @DisplayName("Amount 타입 생성시 percent바우처 타입과 할인금액을 입력받으면 DiscountRate타입을 리턴한다")
  @Test
  void generatePercentDiscount() {
    //given
    VoucherType percent = VoucherType.PERCENT;
    long value = 50L;
    //when
    Amount discount = percent.generateAmount(value);
    //then
    assertInstanceOf(DiscountRate.class, discount);
  }


}
