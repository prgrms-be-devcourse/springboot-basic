package com.programmers.application.domain.voucher;

import com.programmers.application.dto.request.RequestFactory;
import com.programmers.application.dto.request.VoucherCreationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FixedAmountVoucherTest {
    private static final int MIN_DISCOUNT_AMOUNT = 0;
    private static final int MAX_DISCOUNT_AMOUNT = 5000;
    private static final String VOUCHER_TYPE = "fixed";

    @DisplayName("고정 할인 바우처에 0 ~ 5000 범위의 할인양 입력시, discount()를 실행하면 테스트가 성공한다.")
    @ParameterizedTest
    @CsvSource(value = {"100, 1000, 900", "500, 1000, 500"})
    void discount(long discountAmount, long originalPrice, long expectedPrice) {
        //given
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(VOUCHER_TYPE, discountAmount);
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);

        //when
        long discountPrice = voucher.discount(originalPrice);

        //then
        assertThat(discountPrice).isEqualTo(expectedPrice);
    }

    @DisplayName("고정 할인 바우처에 실제 금액보다 큰 할인양 입력시, discount()를 실행하면 0원 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1001, 1000, 0", "2000, 1000, 0"})
    void discountTo0Won(long discountAmount, long originalPrice, long expectedPrice) {
        //given
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(VOUCHER_TYPE, discountAmount);
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);

        //when
        long discountPrice = voucher.discount(originalPrice);

        //then
        assertThat(discountPrice).isEqualTo(expectedPrice);
    }

    @DisplayName("생성할 고정 할인 바우처의 할인양이 0 ~ 5000 범위안에 속하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1000, -1, 5001, 5002, 10000})
    void notIncludeDiscountAmountRange(long discountAmount) {
        //given
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(VOUCHER_TYPE, discountAmount);

        //when, then
        assertThatThrownBy(() -> VoucherFactory.createVoucher(voucherCreationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("%d ~ %d 범위의 바우처 할인양을 입력해주세요. 입력값: %d", MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT, discountAmount));
    }
}
