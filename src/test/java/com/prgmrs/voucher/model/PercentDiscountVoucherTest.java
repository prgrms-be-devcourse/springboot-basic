package com.prgmrs.voucher.model;

import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.wrapper.DiscountValue;
import com.prgmrs.voucher.model.wrapper.Percent;
import com.prgmrs.voucher.util.UUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentDiscountVoucherTest {

    private Percent percent;
    private PercentDiscountStrategy percentDiscountStrategy;
    private UUID voucherId;

    @BeforeEach
    void setUp() {
        voucherId = UUIDGenerator.generateUUID();
        percent = new Percent(50);
        percentDiscountStrategy = new PercentDiscountStrategy(percent);
    }

    @Test
    @DisplayName("주어진 UUID와 바우처 UUID가 같다.")
    void GetVoucherId_NoParam_SameVoucherId() {
        // Given
        Voucher voucher = new Voucher(voucherId, percentDiscountStrategy);

        // When
        UUID returnedVoucherId = voucher.voucherId();

        // Then
        assertThat(returnedVoucherId, is(voucherId));
    }

    @Test
    @DisplayName("주어진 percent가 바우처 내 percent와 같다.")
    void GetAmount_NoParam_SamePercentValue() {
        // Given
        Voucher voucher = new Voucher(voucherId, percentDiscountStrategy);

        // When
        Percent returnedPercent = ((PercentDiscountStrategy) voucher.discountStrategy()).percent();

        // Then
        assertThat(returnedPercent.value(), is(percent.value()));
    }

    @Test
    @DisplayName("할인율에 따라서 기존 금액을 할인한다.")
    void Discount_BeforeDiscountValue_CorrectAterCalculation() {
        // Given
        DiscountValue beforeDiscount = new DiscountValue(1000);
        Voucher voucher = new Voucher(voucherId, percentDiscountStrategy);

        // When
        DiscountValue discountValue = voucher.discount(beforeDiscount);

        // Then
        assertThat(discountValue.value(), is((beforeDiscount.value() / 100 * percent.value())));
    }

    @Test
    @DisplayName("100%를 초과하는 Percent를 생성한다.")
    void Discount_ExceedingDiscountValue_ZeroAfterCalculation() {
        // Given
        int exceedingLimitPercent = 1000;

        // When
        Throwable percentException = assertThrows(WrongRangeFormatException.class, () -> {
            new Percent(exceedingLimitPercent);
        });

        // Then
        assertThat(percentException.getMessage(), is("Percent value must be between 1 and 100."));
    }
}