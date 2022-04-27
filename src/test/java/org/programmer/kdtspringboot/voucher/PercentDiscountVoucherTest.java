package org.programmer.kdtspringboot.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentDiscountVoucherTest {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @Test
    @DisplayName("할인을 해주는 discount 성공테스트")
    void DiscountPercentSuccessesTest() {
        var sut = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
        logger.info("결과값" + sut.discount(200L));
        assertThat(180L).isEqualTo(sut.discount(200L));
    }

    @Test
    @DisplayName("할인퍼센트가이 음수면 안됨")
    void PercentNotMinusTest() {
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), -10L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인퍼센트가 100을 넘어가면 안됨")
    void DiscountNotOver100Test() {
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 101L))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
