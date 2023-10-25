package com.programmers.vouchermanagement.voucher.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherTypeTest {

    @Test
    @DisplayName("없는 바우처 종류를 선택할 시 빈 Optional 객체를 반환한다.")
    void testFindVoucherTypeFailed_ReturnEmptyOptional() {
        //given
        final String desiredVoucherType = "NoCount";

        //when
        final Optional<VoucherType> voucherType = VoucherType.findVoucherType(desiredVoucherType);

        //then
        assertThat(voucherType.isEmpty(), is(true));
    }

    @Test
    @DisplayName("Parameter로 넘겨 받은 소문자 문자열과 비교해 맞는 바우처 종류를 반환한다.")
    void testFindVoucherTypeSuccessful_LowerCase() {
        //given
        final String fixedType = "fixed";
        final String percentType = "percent";

        //when
        final VoucherType fixedVoucherType = VoucherType.findVoucherType(fixedType).get();
        final VoucherType percentVoucherType = VoucherType.findVoucherType(percentType).get();

        //then
        assertThat(fixedVoucherType, is(VoucherType.FIXED));
        assertThat(percentVoucherType, is(VoucherType.PERCENT));
    }
}
