package com.prgrms.devkdtorder.voucher.domain;

import org.assertj.core.data.Offset;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

//assertj 맛보기
class PercentDiscountVoucherTest {

    @Test
    @DisplayName("PercentDiscountVoucher가 올바르게 생성 되어야 한다")
    void testCreation() {

        //given
        UUID voucherId = UUID.randomUUID();
        long percent = 10L;

        //when
        var sut = new PercentDiscountVoucher(voucherId, percent);

        //then
        assertThat(sut.getVoucherId()).isNotNull();

        assertThat(sut.getVoucherId()).isEqualTo(voucherId);
        assertThat(sut.getValue()).isEqualTo(percent);

        assertThat(sut).extracting("voucherId").isEqualTo(voucherId);
        assertThat(sut).extracting("percent").isEqualTo(percent);

        assertThat(sut).isInstanceOf(PercentDiscountVoucher.class);
        assertThat(sut).isInstanceOf(Voucher.class);

        assertThat(sut).isExactlyInstanceOf(PercentDiscountVoucher.class);
        assertThat(sut).isNotExactlyInstanceOf(Voucher.class);

        assertThat(sut.getValue()).isBetween(9L, 11L);
        assertThat(sut.getValue()).isCloseTo(9L, Offset.offset(1L));
        assertThat(sut.getValue()).isCloseTo(9L, Percentage.withPercentage(20));
        assertThat(sut.getValue()).isPositive();
        assertThat(sut.getValue()).isNotZero();
    }

    @Test
    @DisplayName("유효한 범위의 할인 퍼센트로만 생성 해야한다.")
    void testAmountValidation() {
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 0L)).describedAs("퍼센트는 0이 될 수 없다.")
                .isInstanceOf(IllegalArgumentException.class);

        assertThatIllegalArgumentException().describedAs("퍼센트는 음수가 될 수 없다.")
                .isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), -1L));

        assertThatIllegalArgumentException().describedAs("퍼센트는 100을 넘을 수 없다.")
                .isThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), 101L))
                .withMessage("Percent should be less than 100")
                .withMessageContaining("Percent")
                .withMessageNotContaining("Hi");
    }

    @Test
    @DisplayName("주어진 퍼센트 만큼 할인을 해야한다.")
    void testDiscount() {

        //given
        var sutWith50Percent = new PercentDiscountVoucher(UUID.randomUUID(), 50L);
        var sutWith100Percent = new PercentDiscountVoucher(UUID.randomUUID(), 100L);

        //when
        long discounted50PercentPrice = sutWith50Percent.discount(9000L);
        long discounted100PercentPrice = sutWith100Percent.discount(9000L);

        //then
        assertThat(discounted50PercentPrice).isEqualTo(4500L);
        assertThat(discounted100PercentPrice).isEqualTo(0L);
    }

}
