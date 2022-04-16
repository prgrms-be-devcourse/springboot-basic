package com.mountain.voucherApp.utils;

import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.voucher.FixedAmountVoucher;
import com.mountain.voucherApp.voucher.PercentDiscountVoucher;
import com.mountain.voucherApp.voucher.Voucher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static com.mountain.voucherApp.utils.DiscountPolicyUtil.getDiscountPolicyMap;
import static com.mountain.voucherApp.utils.DiscountPolicyUtil.getVoucher;
import static org.assertj.core.api.Assertions.assertThat;

class DiscountPolicyUtilTest {

    @DisplayName("DiscountPolicy 객체를 기준으로 만든 Map 조회")
    @Test
    public void testPolicyMap() {
        //given
        Map<Integer, DiscountPolicy> discountPolicyMap = getDiscountPolicyMap();
        //when
        Arrays.stream(DiscountPolicy.values())
                .forEach((policy) -> {
                    DiscountPolicy getPolicy = discountPolicyMap.get(policy.getPolicyId());
                    //then
                    Assertions.assertEquals(policy, getPolicy);
                });
    }

    @DisplayName("Voucher_정보_잘_가져오는지_테스트_FIXED")
    @Test
    public void fixedVoucherTest() {
        //given
        int fixedSeq = 1;
        int amount = 3000;
        //when
        Voucher fixedVoucher = getVoucher(fixedSeq);
        //then
        assertThat(fixedVoucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(fixedVoucher).isNotInstanceOf((PercentDiscountVoucher.class));
    }

    @DisplayName("Voucher_정보_잘_가져오는지_테스트_percent")
    @Test
    public void percentVoucherTest() {
        //given
        int percentSeq = 2;
        int percentAmount = 15;
        //when
        Voucher fixedVoucher = getVoucher(percentSeq);
        //then
        assertThat(fixedVoucher).isInstanceOf(PercentDiscountVoucher.class);
        assertThat(fixedVoucher).isNotInstanceOf((FixedAmountVoucher.class));
    }

}