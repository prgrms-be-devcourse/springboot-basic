package org.prgrms.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentAmountVoucherTest {

    @DisplayName("퍼센트 범위 실패 테스트")
    @ParameterizedTest
    @ValueSource(doubles = {100, -1})
    void percentDiscountFailTest(double percent) {
        UUID id = UUID.randomUUID();

        assertThatThrownBy(() -> new PercentAmountVoucher(id, percent))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 입력 범위입니다.");
    }

    @DisplayName("퍼센트 범위 성공 테스트")
    @ParameterizedTest
    @ValueSource(doubles = {50})
    void percentDiscountSuccessTest(double percent) {
        UUID id = UUID.randomUUID();
        PercentAmountVoucher voucher = new PercentAmountVoucher(id, percent);
        double stockPrice = 10000;
        double result = voucher.discount(stockPrice);

        assertThat(result).isEqualTo(5000);
    }


}