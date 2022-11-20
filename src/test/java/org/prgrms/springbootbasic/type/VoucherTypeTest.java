package org.prgrms.springbootbasic.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentAmountVoucher;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.prgrms.springbootbasic.type.VoucherType.FIXED;
import static org.prgrms.springbootbasic.type.VoucherType.PERCENT;

class VoucherTypeTest {

    @Test
    @DisplayName("number -> VoucherType : 성공")
    void numberToVoucherType_success() {
        VoucherType fixedType = VoucherType.NumberToVoucherType("1");
        VoucherType percentType = VoucherType.NumberToVoucherType("2");

        assertThat(fixedType, is(FIXED));
        assertThat(percentType, is(PERCENT));
    }

    @Test
    @DisplayName("number -> VoucherType : 실패")
    void numberToVoucherType_fail() {
        assertThrows(NoSuchElementException.class, () -> VoucherType.NumberToVoucherType("3"));
    }

    @Test
    void classToVoucherType() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), anyLong());
        PercentAmountVoucher percentAmountVoucher = new PercentAmountVoucher(UUID.randomUUID(), anyLong());

        assertThat(VoucherType.ClassToVoucherType(fixedAmountVoucher), is(FIXED));
        assertThat(VoucherType.ClassToVoucherType(percentAmountVoucher), is(PERCENT));
    }

    @Test
    void isFixed_success() {
        assertThat(VoucherType.isFixed("1"), is(true));
    }

    @Test
    void isFixed_fail() {
        assertThat(VoucherType.isFixed("3"), is(false));
    }

    @Test
    void isPercent_success() {
        assertThat(VoucherType.isPercent("2"), is(true));
    }

    @Test
    void isPercent_fail() {
        assertThat(VoucherType.isPercent("4"), is(false));
    }
}