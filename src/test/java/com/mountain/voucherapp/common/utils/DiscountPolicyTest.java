package com.mountain.voucherapp.common.utils;

import com.mountain.voucherapp.model.enums.DiscountPolicy;
import com.mountain.voucherapp.model.vo.voucher.FixedAmountVoucher;
import com.mountain.voucherapp.model.vo.voucher.PercentDiscountVoucher;
import com.mountain.voucherapp.model.vo.voucher.Voucher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountPolicyTest {

    @DisplayName("DiscountPolicy 객체를 기준으로 만든 Map 조회")
    @Test
    void testPolicyMap() {

        Arrays.stream(DiscountPolicy.values())
                .forEach((policy) -> {
                    Optional<DiscountPolicy> getPolicy = DiscountPolicy.find(policy.getSeq());
                    //then
                    Assertions.assertEquals(policy, getPolicy.get());
                });
    }

    @DisplayName("Voucher_정보_잘_가져오는지_테스트_FIXED")
    @Test
    void fixedVoucherTest() {
        //given
        //when
        Voucher fixedVoucher = DiscountPolicy.FIXED.getVoucher();
        //then
        assertThat(fixedVoucher)
                .isInstanceOf(FixedAmountVoucher.class)
                .isNotInstanceOf((PercentDiscountVoucher.class));
    }

    @DisplayName("Voucher_정보_잘_가져오는지_테스트_percent")
    @Test
    void percentVoucherTest() {
        //given
        int percentSeq = 2;
        //when
        Voucher percentVoucher = DiscountPolicy.PERCENT.getVoucher();
        //then
        assertThat(percentVoucher)
                .isInstanceOf(PercentDiscountVoucher.class)
                .isNotInstanceOf((FixedAmountVoucher.class));
    }

}