package com.programmers.vouchermanagement.voucher.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherTypeTest {

    @Test
    @DisplayName("없는 바우처 종류를 선택할 시 빈 Optional 객체를 반환한다.")
    void testFindVoucherTypeFailed_ReturnEmptyOptional() {
        //given
        String desiredVoucherType = "NoCount";

        //when
        assertThatThrownBy(() -> VoucherType.findVoucherTypeByName(desiredVoucherType))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Parameter로 넘겨 받은 소문자 문자열과 비교해 맞는 바우처 종류를 반환한다.")
    void testFindVoucherTypeSuccessful_LowerCase() {
        //given
        String fixedType = "fixed";
        String percentType = "percent";

        //when
        VoucherType fixedVoucherType = VoucherType.findVoucherTypeByName(fixedType);
        VoucherType percentVoucherType = VoucherType.findVoucherTypeByName(percentType);

        //then
        assertThat(fixedVoucherType, is(VoucherType.FIXED));
        assertThat(percentVoucherType, is(VoucherType.PERCENT));
    }

    @Test
    @DisplayName("Parameter로 넘겨 받은 문자열의 대소문자 구분 없이 해당하는 바우처 종류를 반환한다.")
    void testFindVoucherTypeSuccessful_CaseInsensitive() {
        //given
        String fixedType = "FixeD";
        String percentType = "PERCENT";

        //when
        VoucherType fixedVoucherType = VoucherType.findVoucherTypeByName(fixedType);
        VoucherType percentVoucherType = VoucherType.findVoucherTypeByName(percentType);

        //then
        assertThat(fixedVoucherType, is(VoucherType.FIXED));
        assertThat(percentVoucherType, is(VoucherType.PERCENT));
    }
}
