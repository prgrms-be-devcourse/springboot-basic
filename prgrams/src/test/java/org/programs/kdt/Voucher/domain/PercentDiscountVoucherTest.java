package org.programs.kdt.Voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programs.kdt.Exception.InvalidValueException;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

  @Test
  @DisplayName("주어진 금액만큼 할인을 해야한다.")
  void testDiscount() {
    var voucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);
    assertEquals(800, voucher.discount(1000));
  }

  @Test
  @DisplayName("할인 금액은 마이너스가 될 수 없다.")
  void testWithMinus() {
    assertThrows(
        InvalidValueException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -100));
  }

  @Test
  @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
  void testVoucherCreation() {
    assertAll(
        "PercentDiscountVoucher creation",
        () ->
            assertThrows(
                InvalidValueException.class,
                () -> new PercentDiscountVoucher((UUID.randomUUID()), 0)),
        () ->
            assertThrows(
                InvalidValueException.class,
                () -> new PercentDiscountVoucher((UUID.randomUUID()), -1)),
        () ->
            assertThrows(
                InvalidValueException.class,
                () -> new PercentDiscountVoucher((UUID.randomUUID()), 101)));
  }

  @Test
  @DisplayName("바우처 타입은 PERCENT타입을 가진다")
  void testVoucherType() {
    var voucher = new PercentDiscountVoucher(UUID.randomUUID(), 50);
    assertThat(voucher.getVoucherType(), is(VoucherType.PERCENT));
  }

  @Test
  @DisplayName("value의 값이 정상적으로 변경된다.")
  void testVoucherValue() {
    var voucher = new PercentDiscountVoucher(UUID.randomUUID(), 50);
    voucher.changeValue(99L);
    assertThat(voucher.getValue(), is(99L));
  }

  @Test
  @DisplayName("할인 금액은 마이너스로 변경될 수 없다..")
  void testChangeWithMinus() {
    var voucher = new PercentDiscountVoucher((UUID.randomUUID()), 99);

    assertThrows(InvalidValueException.class, () -> voucher.changeValue(-100L));
  }

  @Test
  @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
  void testVoucherVaindationChange() {
    var voucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 50);
    var voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 50);
    var voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 50);
    assertAll(
        "PercentDiscountVoucher( update",
        () -> assertThrows(InvalidValueException.class, () -> voucher1.changeValue(-100L)),
        () -> assertThrows(InvalidValueException.class, () -> voucher2.changeValue(0L)),
        () -> assertThrows(InvalidValueException.class, () -> voucher3.changeValue(101L)));
  }
}
