package team.marco.voucher_management_system.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.FIXED;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.PERCENT;

class VoucherTest {

    @DisplayName("필수값만(아이디, 쿠폰 종류, 할인값)으로 쿠폰을 생성할 수 있다.")
    @Test
    void createVoucherByBuilder() {
        // given
        Long id = 1L;
        VoucherType voucherType = FIXED;
        int discountValue = 10_000;

        // when
        Voucher voucher = new Voucher.Builder(id, voucherType, discountValue).build();

        // then
        assertThat(voucher)
                .extracting("id", "voucherType", "discountValue")
                .contains(id, voucherType, discountValue);
        assertThat(voucher.getCode()).isNotNull();
        assertThat(voucher.getName()).isEqualTo("10,000원 할인 쿠폰");
    }

    @DisplayName("고정 금액 쿠폰은 1,000원이상 할인해야 합니다.")
    @Test
    void createFixedVoucherWithMinValue() {
        // given
        int lessAmount = 990;
        int minAmount = 1_000;

        // when then
        assertThatNoException().isThrownBy(() -> new Voucher.Builder(1L, FIXED, minAmount).build());
        assertThatThrownBy(() -> new Voucher.Builder(2L, FIXED, lessAmount).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상, 100,000원 이하이어야 합니다.");
    }

    @DisplayName("고정 금액 쿠폰은 10만원이하로 할인해야 합니다.")
    @Test
    void createFixedVoucherWithMaxValue() {
        // given
        int tooMuchAmount = 1000_010;
        int maxAmount = 100_000;

        // when then
        assertThatNoException().isThrownBy(() -> new Voucher.Builder(1L, FIXED, maxAmount).build());
        assertThatThrownBy(() -> new Voucher.Builder(2L, FIXED, tooMuchAmount).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상, 100,000원 이하이어야 합니다.");
    }

    @DisplayName("% 할인 쿠폰은 5% 이상 할인해야 합니다.")
    @Test
    void createPercentVoucherWithMinValue() {
        // given
        int lessPercent = 4;
        int minPercent = 5;

        // when then
        assertThatNoException().isThrownBy(() -> new Voucher.Builder(1L, PERCENT, minPercent).build());
        assertThatThrownBy(() -> new Voucher.Builder(2L, PERCENT, lessPercent).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 비율은 5% 이상, 100% 이하이어야 합니다.");
    }

    @DisplayName("% 할인 쿠폰은 100% 이하로 할인해야 합니다.")
    @Test
    void createPercentVoucherWithMaxValue() {
        // given
        int tooMuchPercent = 101;
        int maxPercent = 100;

        // when then
        assertThatNoException().isThrownBy(() -> new Voucher.Builder(1L, PERCENT, maxPercent).build());
        assertThatThrownBy(() -> new Voucher.Builder(2L, PERCENT, tooMuchPercent).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 비율은 5% 이상, 100% 이하이어야 합니다.");
    }
}