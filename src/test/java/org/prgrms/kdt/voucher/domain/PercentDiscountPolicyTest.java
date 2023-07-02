package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.exception.InvalidDiscountException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountPolicyTest {

    @Test
    @DisplayName("PercentDiscountPolicy 객체 생성 시 검증 테스트")
    void validate(){
        assertThrows(InvalidDiscountException.class, () ->{
            new PercentDiscountPolicy(200.0);
        });
    }

    @Test
    @DisplayName("퍼센트 할인이 정상적으로 계산되는지 테스트")
    void applyDiscount(){
        //given
        DiscountPolicy percentDiscountPolicy = new PercentDiscountPolicy(20.0);

        //when
        double discountPrice = percentDiscountPolicy.applyDiscount(100.0);

        //then
        assertThat(discountPrice, is(80.0));
    }
}