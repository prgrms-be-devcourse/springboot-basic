package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.exception.InvalidDiscountException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountPolicyTest {

    @Test
    void validate(){
        assertThrows(InvalidDiscountException.class, () ->{
            new PercentDiscountPolicy(200.0);
        });
    }

    @Test
    void applyDiscount(){
        //given
        DiscountPolicy percentDiscountPolicy = new PercentDiscountPolicy(20.0);

        //when
        double discountPrice = percentDiscountPolicy.applyDiscount(100.0);

        //then
        assertThat(discountPrice, is(80.0));
    }
}