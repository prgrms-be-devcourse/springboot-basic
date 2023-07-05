package com.dev.voucherproject.voucher;

import com.dev.voucherproject.model.voucher.Voucher;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PercentDiscountVoucherTest {
    @ParameterizedTest
    @CsvSource(
        {
            "10, 10000, 9000",
            "30, 10000, 7000",
            "50, 10000, 5000",
            "100, 10000, 0"
        }
    )
    @DisplayName("할인이 정상적으로 적용된다.")
    public void percentDiscountIsNormalValue_Then_Return_DiscountedAmount(long percent, long beforeDiscount, int expectedDiscount) {
        //GIVEN
        Voucher voucher = Voucher.of(UUID.randomUUID(), VoucherPolicy.PERCENT_DISCOUNT_VOUCHER, percent);

        //WHEN
        long actual = voucher.discount(beforeDiscount);

        //THEN
        assertThat(actual).isEqualTo(expectedDiscount);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 101L, 200L})
    @DisplayName("바우처의 할인률이 0~100%가 아닐 경우 예외가 발생한다.")
    void wrongInput_Then_Exception(long percent) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() ->
                Voucher.of(UUID.randomUUID(), VoucherPolicy.PERCENT_DISCOUNT_VOUCHER, percent)
            );
    }
}
