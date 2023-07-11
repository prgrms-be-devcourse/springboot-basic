package com.prgmrs.voucher.model;

import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.model.vo.Percent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentDiscountVoucherTest {
    @Test
    @DisplayName("주어진 UUID와 바우처 UUID가 같다.")
    void getVoucherIdTest() {
        UUID voucherId = UUID.randomUUID();
        Percent percent = new Percent(50);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        Voucher voucher = new Voucher(voucherId, percentDiscountStrategy);

        UUID returnedVoucherId = voucher.voucherId();

        assertThat(returnedVoucherId, is(voucherId));
    }

    @Test
    @DisplayName("주어진 percent가 바우처 내 percent와 같다.")
    void getAmountTest() {
        UUID voucherId = UUID.randomUUID();
        Percent percent = new Percent(50);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        Voucher voucher = new Voucher(voucherId, percentDiscountStrategy);

        Percent returnedPercent = ((PercentDiscountStrategy)voucher.discountStrategy()).percent();

        assertThat(returnedPercent.value(), is(percent.value()));
    }

    @Test
    @DisplayName("할인율에 따라서 기존 금액을 할인한다.")
    void discountTest() {
        UUID voucherId = UUID.randomUUID();
        Percent percent = new Percent(34);
        DiscountValue beforeDiscount = new DiscountValue(1000);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        Voucher voucher = new Voucher(voucherId, percentDiscountStrategy);
        DiscountValue discountValue = voucher.discount(beforeDiscount);

        assertThat(discountValue.value(), is( (beforeDiscount.value()/100 * percent.value()) ));
    }

    @Test
    @DisplayName("100%를 초과하는 Percent를 생성한다.")
    void discountAmountIsSmallerThanValueBeforeDiscount() {
        Throwable percentException = assertThrows(IllegalArgumentException.class, () -> {
           new Percent(1000);
        });

        assertThat(percentException.getMessage(), is("Percent value must be between 1 and 100."));
    }
}