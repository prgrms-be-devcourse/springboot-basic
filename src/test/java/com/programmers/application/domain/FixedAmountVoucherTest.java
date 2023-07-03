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

class FixedAmountVoucherTest {
    private static final int MIN_DISCOUNT_AMOUNT = 0;
    private static final int MAX_DISCOUNT_AMOUNT = 5000;
    private static final String VOUCHER_TYPE = "fixed";

    @DisplayName("고정 할인 바우처 discount() 메서드 성공 테스트")
    @ParameterizedTest
    @CsvSource(value = {"100, 1000, 900", "500, 1000, 500"})
    void discount(long discountAmount, long originalPrice, long expectedPrice) {
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(VOUCHER_TYPE, discountAmount);
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);

        long discountPrice = voucher.discount(originalPrice);

        assertThat(discountPrice).isEqualTo(expectedPrice);
    }

    @DisplayName("실제 금액보다 할인양이 클 경우 0원 반환 테스트")
    @ParameterizedTest
    @CsvSource(value = {"1001, 1000, 0", "2000, 1000, 0"})
    void discountTo0Won(long discountAmount, long originalPrice, long expectedPrice) {
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(VOUCHER_TYPE, discountAmount);
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);
        assertThat(voucher.discount(originalPrice)).isEqualTo(expectedPrice);
    }

    @DisplayName("생성할 고정 할인 바우처의 할인양이 범위안에 속하지 않을 경우 예외 발생 테스트")
    @ParameterizedTest
    @ValueSource(longs = {-1000, -1, 5001, 5002, 10000})
    void notIncludeDiscountAmountRange(long discountAmount) {
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(VOUCHER_TYPE, discountAmount);
        Assertions.assertThatThrownBy(() -> VoucherFactory.createVoucher(voucherCreationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MIN_DISCOUNT_AMOUNT + " ~ " + MAX_DISCOUNT_AMOUNT + " 범위의 바우처 할인양을 입력해주세요. " + "입력값: " + discountAmount);
    }
}
