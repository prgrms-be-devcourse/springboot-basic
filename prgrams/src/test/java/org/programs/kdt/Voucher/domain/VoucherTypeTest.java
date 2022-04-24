package org.programs.kdt.Voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherTypeTest {
  @ParameterizedTest
  @DisplayName("문자열들로 Voucher 타입을 가져올 수 있다.")
  @ValueSource(strings = {"percent", "fixedAmount"})
  void StringToCommandTest(String s) {
    assertThat(VoucherType.findVoucherType(s).getClass(), is(VoucherType.class));
  }

  @Test
  @DisplayName("문자열 percent로 Voucher타입 percent를 가져온다")
  void percentTOVoucherPercent() {
    String s = "percent";
    assertThat(VoucherType.findVoucherType(s), is(VoucherType.PERCENT));
  }

  @Test
  @DisplayName("문자열 fixedAmount로 Voucher타입 fixedAmount를 가져온다")
  void fixedAmountTOVoucherFixedAmount() {
    String s = "fixedAmount";
    assertThat(VoucherType.findVoucherType(s), is(VoucherType.FIXEDAMOUNT));
  }

  @Test
  @DisplayName("percent 바우처타입으로 PercentDiscountVoucher를 만들수 있다.")
  void createPercentVoucher() {
    VoucherType percent = VoucherType.PERCENT;
    UUID uuid = UUID.randomUUID();
    long value = 30L;
    LocalDateTime createdAt = LocalDateTime.now();
    var voucher = percent.createVoucher(uuid, value, createdAt);
    assertThat(voucher.getClass(), is(PercentDiscountVoucher.class));
    assertThat(voucher.getVoucherId(), is(uuid));
    assertThat(voucher.getVoucherType(), is(percent));
    assertThat(voucher.getCreatedAt(), is(createdAt));
    assertThat(voucher.getValue(), is(value));
  }

  @Test
  @DisplayName("Fiexed 바우처타입으로 FiexedAmountVoucher를 만들수 있다.")
  void createFixedVoucher() {
    VoucherType amount = VoucherType.FIXEDAMOUNT;
    UUID uuid = UUID.randomUUID();
    long value = 3000L;
    LocalDateTime createdAt = LocalDateTime.now();
    var voucher = amount.createVoucher(uuid, value, createdAt);
    assertThat(voucher.getClass(), is(FixedAmountVoucher.class));
    assertThat(voucher.getVoucherId(), is(uuid));
    assertThat(voucher.getVoucherType(), is(amount));
    assertThat(voucher.getCreatedAt(), is(createdAt));
    assertThat(voucher.getValue(), is(value));
  }
}
