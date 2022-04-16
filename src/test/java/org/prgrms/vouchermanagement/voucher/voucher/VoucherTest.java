package org.prgrms.vouchermanagement.voucher.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class VoucherTest {

  Logger log = LoggerFactory.getLogger(VoucherTest.class);

  @Test
  void testVoucherTypeConversion() {
    Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now());
    assertThat(VoucherType.toDbValue(fixedAmountVoucher), is(1));
    Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now());
    assertThat(VoucherType.toDbValue(percentDiscountVoucher), is(2));
  }
  @Test
  @DisplayName("Voucher는 Voucher 클래스를 상속 받아야 한다")
  void testVoucherInheritance() {
    FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now());
    assertThat(
      (fixedAmountVoucher instanceof Voucher),
      is(true));

    PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now());
    assertThat((percentDiscountVoucher instanceof Voucher),
      is(true));
  }

  @Test
  @DisplayName("번호로 생성할 Voucher를 고를 수 있다")
  void testConvertNumberToVoucherType() {
    assertThat(VoucherType.fromDbValue(1), is(VoucherType.FIXED_AMOUNT));
    assertThat(VoucherType.fromDbValue(2), is(VoucherType.PERCENT_DISCOUNT));
  }

  @Test
  @DisplayName("선택지를 벗어나는 경우 에러가 발생한다")
  void testConvertNumberToVoucherTypeException() {
    assertThrows(RuntimeException.class, () -> VoucherType.fromDbValue(0));
    assertThrows(RuntimeException.class, () -> VoucherType.fromDbValue(3));
  }

  @Test
  @DisplayName("FixedAmountVoucher 클래스의 discount() 메서드 테스트")
  void testFixedAmountVoucherDiscount() {
    FixedAmountVoucher fixedAmountVoucher;
    fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, LocalDateTime.now());
    assertThat(fixedAmountVoucher.discount(1000L), is(900L));
    assertThat(fixedAmountVoucher.discount(100L), is(0L));
    assertThat(fixedAmountVoucher.discount(10L), is(0L));

    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, LocalDateTime.now());
    log.info("voucher class = {}", voucher.getClass().getSimpleName());
  }

  @Test
  @DisplayName("PercentDiscountVoucher 클래스의 discount() 메서드 테스트")
  void testPercentDiscountVoucherDiscount() {
    PercentDiscountVoucher percentDiscountVoucher;
    percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now());
    assertThat(percentDiscountVoucher.discount(1000L), is(900L));
    assertThat(percentDiscountVoucher.discount(100L), is(90L));
    assertThat(percentDiscountVoucher.discount(10L), is(9L));
  }

  @Test
  @DisplayName("VoucherFactory로 Voucher를 생성할 수 있어야 한다")
  void testVoucherFactory() {
    Voucher voucher = VoucherFactory.createVoucher(1, 1000, LocalDateTime.now());
    assertThat(voucher instanceof FixedAmountVoucher, is(true));

    voucher = VoucherFactory.createVoucher(2, 10, LocalDateTime.now());
    assertThat(voucher instanceof PercentDiscountVoucher, is(true));
  }

  @Test
  @DisplayName("reduction의 범위에 벗어나는 Voucher을 생성할 수는 없다")
  void testVoucherFactoryException() {
    assertThrows(RuntimeException.class, () -> VoucherFactory.createVoucher(1, 10_000_000, LocalDateTime.now()));
    assertThrows(RuntimeException.class, () -> VoucherFactory.createVoucher(1, 0, LocalDateTime.now()));
    assertThrows(RuntimeException.class, () -> VoucherFactory.createVoucher(2, 101, LocalDateTime.now()));
    assertThrows(RuntimeException.class, () -> VoucherFactory.createVoucher(2, 0, LocalDateTime.now()));
  }
}
