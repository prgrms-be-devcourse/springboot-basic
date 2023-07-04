package org.prgrms.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.application.domain.voucher.PercentAmountVoucher;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.prgrms.application.domain.voucher.VoucherType.PERCENT;

class PercentAmountVoucherTest {
    Random random = new Random();

    @DisplayName("퍼센트 범위 실패 테스트")
    @ParameterizedTest
    @ValueSource(doubles = {100, -1})
    void percentDiscountFailTest(double percent) {
        long randomId = Math.abs(random.nextLong());

        assertThatThrownBy(() -> new PercentAmountVoucher(randomId, PERCENT, percent))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("퍼센트 범위 성공 테스트")
    @ParameterizedTest
    @ValueSource(doubles = {50})
    void percentDiscountSuccessTest(double percent) {
        long randomId = Math.abs(random.nextLong());

        PercentAmountVoucher voucher = new PercentAmountVoucher(randomId, PERCENT, percent);
        double stockPrice = 10000;
        double result = voucher.discount(stockPrice);

        assertThat(result).isEqualTo(5000);
    }


}