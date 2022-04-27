package org.prgms.domain;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class VoucherTest {

    @ParameterizedTest
    @NullSource
    @DisplayName("바우처 id는 null 일 수 없음")
    void idNotNullTest(UUID id) {
        assertThatIllegalArgumentException().isThrownBy(() -> new FixedAmountVoucher(id, 10L));
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(id, 10L));
    }


    @Test
    @DisplayName("FixedVoucher 할인 적용 테스트")
    void fixedVoucherApplyTest() {
        val fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);

        assertThat(fixedVoucher.apply(100)).isEqualTo(100 - fixedVoucher.getDiscountAmount());
    }

    @Test
    @DisplayName("PercentVoucher 할인 적용 테스트")
    void percentVoucherApplyTest() {
        val percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        assertThat(percentVoucher.apply(1000)).isEqualTo((long) (1000 * (1 - percentVoucher.getDiscountAmount() / 100.0)));
    }
}