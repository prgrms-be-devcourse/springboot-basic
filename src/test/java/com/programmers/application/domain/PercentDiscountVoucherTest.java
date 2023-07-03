package com.programmers.application.domain;

import com.programmers.application.domain.voucher.Voucher;
import com.programmers.application.domain.voucher.VoucherFactory;
import com.programmers.application.dto.request.RequestFactory;
import com.programmers.application.dto.request.VoucherCreationRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountVoucherTest {
    private static final int MIN_DISCOUNT_AMOUNT = 0;
    private static final int MAX_DISCOUNT_AMOUNT = 100;
    private static final String VOUCHER_TYPE = "percent";

    @DisplayName("정률 할인 바우처에 0 ~ 100 범위의 할인양 입력시, discount()를 실행하면 테스트가 성공한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "10, 1000, 900",
            "20, 1000, 800",
            "50, 1000, 500",
            "100, 1000, 0"})
    void discount(long discountAmount, long originalPrice, long expectedPrice) {
        //given
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(VOUCHER_TYPE, discountAmount);
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);

        //when
        long discountPrice = voucher.discount(originalPrice);

        //then
        assertThat(discountPrice).isEqualTo(expectedPrice);
    }

    @DisplayName("생성할 정률 할인 바우처의 할인양이 0 ~ 100 범위안에 속하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1000, -1, 101, 102, 1003})
    void notIncludeDiscountAmountRange(long discountAmount) {
        //given
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(VOUCHER_TYPE, discountAmount);

        //when, then
        Assertions.assertThatThrownBy(() -> VoucherFactory.createVoucher(voucherCreationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MIN_DISCOUNT_AMOUNT + " ~ " + MAX_DISCOUNT_AMOUNT + " 범위의 바우처 할인양을 입력해주세요. " + "입력값: " + discountAmount);
    }
}
