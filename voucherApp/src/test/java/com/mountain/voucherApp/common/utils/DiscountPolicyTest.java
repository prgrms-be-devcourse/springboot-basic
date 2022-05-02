package com.mountain.voucherApp.common.utils;

import com.mountain.voucherApp.model.enums.DiscountPolicy;
import com.mountain.voucherApp.model.vo.voucher.FixedAmountVoucher;
import com.mountain.voucherApp.model.vo.voucher.PercentDiscountVoucher;
import com.mountain.voucherApp.model.vo.voucher.Voucher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountPolicyTest {

    @DisplayName("DiscountPolicy 객체를 기준으로 만든 Map 조회")
    @Test
    public void testPolicyMap() {

        Arrays.stream(DiscountPolicy.values())
                .forEach((policy) -> {
                    DiscountPolicy getPolicy = DiscountPolicy.find(policy.ordinal());
                    //then
                    Assertions.assertEquals(policy, getPolicy);
                });
    }

    @DisplayName("Voucher_정보_잘_가져오는지_테스트_FIXED")
    @Test
    public void fixedVoucherTest() {
        //given
        //when
        Voucher fixedVoucher = DiscountPolicy.FIXED.getVoucher();
        //then
        assertThat(fixedVoucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(fixedVoucher).isNotInstanceOf((PercentDiscountVoucher.class));
    }

    @DisplayName("Voucher_정보_잘_가져오는지_테스트_percent")
    @Test
    public void percentVoucherTest() {
        //given
        int percentSeq = 2;
        //when
        Voucher percentVoucher = DiscountPolicy.PERCENT.getVoucher();
        //then
        assertThat(percentVoucher).isInstanceOf(PercentDiscountVoucher.class);
        assertThat(percentVoucher).isNotInstanceOf((FixedAmountVoucher.class));
    }

}