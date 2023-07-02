package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.exception.InvalidDiscountException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class FixedDiscountPolicyTest {

    @Test
    @DisplayName("FixedDiscountPolicy객체 생성 시 검증 테스트")
    void validate(){
        assertThrows(InvalidDiscountException.class, () ->{
            new FixedDiscountPolicy(-5);
        });
    }

    @Test
    @DisplayName("고정할인이 정상적으로 계산되는지 테스트")
    void applyDiscount(){
        //given
        DiscountPolicy fixedDiscountPolicy = new FixedDiscountPolicy(30.0);

        //when
        double discountPrice = fixedDiscountPolicy.applyDiscount(100.0);

        //then
        assertThat(discountPrice, is(70.0));
    }
}