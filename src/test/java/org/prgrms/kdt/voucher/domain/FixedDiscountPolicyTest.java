package org.prgrms.kdt.voucher.domain;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.exception.InvalidDiscountException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class FixedDiscountPolicyTest {

    @Test
    void validate(){
        assertThrows(InvalidDiscountException.class, () ->{
            new FixedDiscountPolicy(-5);
        });
    }

    @Test
    void applyDiscount(){
        //given
        DiscountPolicy fixedDiscountPolicy = new FixedDiscountPolicy(30.0);

        //when
        double discountPrice = fixedDiscountPolicy.applyDiscount(100.0);

        //then
        assertThat(discountPrice, is(70.0));
    }
}