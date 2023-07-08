package org.prgrms.kdt.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.voucher.Voucher;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class VoucherTypeTest {

    @Test
    @DisplayName("바우처 String 으로 FIXED를 입력하였을 경우")
    public void testFixed() {
        //given
        VoucherType voucherType = VoucherType.of("FIXED");
        Long discount = 1000L;
        //when
        Voucher voucher = voucherType.makeVoucher(discount);
        //then
        assertThat(voucher.getVoucherType(),is(VoucherType.FIXED));
    }

    @Test
    @DisplayName("바우처 String 으로 FIX 를 입력하였을 경우")
    public void testFix() {
        //given
        VoucherType voucherType = VoucherType.of("FIX");
        Long discount = 1000L;
        //when
        Voucher voucher = voucherType.makeVoucher(discount);
        //then
        assertThat(voucher.getVoucherType(),is(VoucherType.FIXED));
    }

    @Test
    @DisplayName("바우처 String 으로 PERCENT를 입력하였을 경우")
    public void testPercent() {
        //given
        VoucherType voucherType = VoucherType.of("PERCENT");
        Long discount = 10L;
        //when
        Voucher voucher = voucherType.makeVoucher(discount);
        //then
        assertThat(voucher.getVoucherType(),is(VoucherType.PERCENT));
    }

    @Test
    @DisplayName("존재하지 않는 바우처 타입이 입력될 경우 예외 밠생")
    public void testUnknown() {
        //given
        String input = "UNKNOWN";

        // when & then
        assertThatThrownBy(() -> VoucherType.of(input))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("입력이 잘못되었습니다.");
    }


}