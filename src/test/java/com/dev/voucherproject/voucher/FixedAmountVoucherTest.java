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

public class FixedAmountVoucherTest {
    @DisplayName("할인이 정상적으로 적용된다.")
    @ParameterizedTest
    @CsvSource(
        {
            "5000, 10000, 5000",
            "500, 6500, 6000",
            "10000, 10000, 0"
        }
    )
    public void fixedAmountIsNormalValue_Then_Return_DiscountedAmount(long amount, long beforeDiscount, int afterDiscount) {
        //GIVEN
        Voucher voucher = Voucher.of(UUID.randomUUID(), VoucherPolicy.FIXED_AMOUNT_VOUCHER, amount);

        //WHEN
        long actual = voucher.discount(beforeDiscount);

        //THEN
        assertThat(actual).isEqualTo(afterDiscount);
    }

    @DisplayName("바우처의 할인금액이 주문금액보다 클 경우 최종 주문금액은 0이 반환된다.")
    @ParameterizedTest
    @CsvSource(
        {
            "5000, 4500, 0",
            "500, 250, 0",
            "10000, 9000, 0"
        }
    )
    public void fixedAmountIsGreaterThanOrderPrice_Then_Return_Zero(long amount, long beforeDiscount, int afterDiscount) {
        //GIVEN
        Voucher voucher = Voucher.of(UUID.randomUUID(), VoucherPolicy.FIXED_AMOUNT_VOUCHER, amount);

        //WHEN
        long actual = voucher.discount(beforeDiscount);

        //THEN
        assertThat(actual).isEqualTo(afterDiscount);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1000L, -200L})
    @DisplayName("바우처의 할인금액이 음수일 경우 예외가 발생한다.")
    void wrongInput_Then_Exception(long amount) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() ->
                Voucher.of(UUID.randomUUID(), VoucherPolicy.FIXED_AMOUNT_VOUCHER, amount)
            );
    }
}
