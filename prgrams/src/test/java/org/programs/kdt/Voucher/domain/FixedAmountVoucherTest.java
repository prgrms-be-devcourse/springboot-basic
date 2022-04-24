package org.programs.kdt.Voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programs.kdt.Exception.InvalidValueException;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

class FixedAmountVoucherTest {
  @Test
  @DisplayName("주어진 금액만큼 할인을 해야한다.")
  void testDiscount() {
    var sut = new FixedAmountVoucher(UUID.randomUUID(), 100);
    assertEquals(900, sut.discount(1000));
  }

  @Test
  @DisplayName("디스카운트된 금액은 마이너스가 될 수 없다.")
  void testMinusDiscountedAmount() {
    var sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);
    assertEquals(0, sut.discount(900));
  }

  @Test
  @DisplayName("할인 금액은 마이너스가 될 수 없다.")
  void testWithMinus() {
    assertThrows(
        InvalidValueException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
  }

  @Test
  @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
  void testVoucherCreation() {
    assertAll(
        "FixedAmountVoucher creation",
        () ->
            assertThrows(
                InvalidValueException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
        () ->
            assertThrows(
                InvalidValueException.class,
                () -> new FixedAmountVoucher(UUID.randomUUID(), -1)),
        () ->
            assertThrows(
                InvalidValueException.class,
                () -> new FixedAmountVoucher(UUID.randomUUID(), 10001)));
  }

  @Test
  @DisplayName("바우처 타입은 FIXEDAMOUNT타입을 가진다")
  void testVoucherType() {
    var voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
    assertThat(voucher.getVoucherType(), is(VoucherType.FIXEDAMOUNT));
  }

  @Test
  @DisplayName("value의 값이 정상적으로 변경된다.")
  void testVoucherValue() {
    var voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
    voucher.changeValue(200L);
    assertThat(voucher.getValue(), is(200L));
  }

  @Test
  @DisplayName("할인 금액은 마이너스로 변경될 수 없다..")
  void testChangeWithMinus() {
    var voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

    assertThrows(InvalidValueException.class, () -> voucher.changeValue(-100L));
  }

  @Test
  @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
  void testVoucherVaindationChange() {
    var voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 100);
    var voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 100);
    var voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 100);
    assertAll(
        "FixedAmountVoucher update",
        () -> assertThrows(InvalidValueException.class, () -> voucher1.changeValue(-100L)),
        () -> assertThrows(InvalidValueException.class, () -> voucher2.changeValue(0L)),
        () -> assertThrows(InvalidValueException.class, () -> voucher3.changeValue(10001L)));
  }
}
