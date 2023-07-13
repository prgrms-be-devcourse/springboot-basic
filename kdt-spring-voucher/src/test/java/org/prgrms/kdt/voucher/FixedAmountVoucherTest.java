package org.prgrms.kdt.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.AppConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("할인권 금액과 상품 가격이 같은 경우")
    void discountAppliedSamePrice() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 3000);
        long beforePrice = 3000;

        //when
        long resultPrice = fixedAmountVoucher.discountAppliedPrice(beforePrice);

        //then
        assertThat(resultPrice).isEqualTo(0);
    }

    @Test
    @DisplayName("할인권 금액이 더 큰 경우")
    void largeDiscountPrice() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 4000);
        long beforePrice = 3000;

        //when
        long resultPrice = fixedAmountVoucher.discountAppliedPrice(beforePrice);

        //then
        assertThat(resultPrice).isEqualTo(0);
    }

    @Test
    @DisplayName("물건의 금액이 더 큰 경우")
    void largeProductPrice(){
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 3000);
        long beforePrice = 5000;

        //when
        long resultPrice = fixedAmountVoucher.discountAppliedPrice(beforePrice);

        //then
        assertThat(resultPrice).isEqualTo(beforePrice-fixedAmountVoucher.getVoucherDiscountValue());
    }
}