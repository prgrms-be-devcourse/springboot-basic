package com.programmers.kdtspringorder.voucher.domain;

import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("할인율 정상 테스트")
    public void discount() throws Exception {
        //given
        PercentDiscountVoucher percentDiscountVoucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 20L);
        PercentDiscountVoucher percentDiscountVoucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 1L);
        PercentDiscountVoucher percentDiscountVoucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 50L);

        //when
        long discount1 = percentDiscountVoucher1.discount(100);
        long discount2 = percentDiscountVoucher2.discount(100);
        long discount3 = percentDiscountVoucher3.discount(100);

        //then
        assertThat(discount1).isEqualTo(80L);
        assertThat(discount2).isEqualTo(99L);
        assertThat(discount3).isEqualTo(50L);
    }

    @ParameterizedTest(name = "{index} {displayName} percent = {0}")
    @ValueSource(ints = {-10, -1, 51, 100})
    @DisplayName("할인율은 0 초과 50 이하입니다.")
    public void parameterizedDiscountRateTest(Integer percent) {
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), percent));
    }

    @Test
    @DisplayName("할인율은 0초과 50 이하 입니다.")
    public void discountRateTest() throws Exception {
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), -1));
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 0));
        assertThatIllegalArgumentException().isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 51));
    }
}