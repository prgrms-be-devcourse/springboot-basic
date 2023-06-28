package com.devcourse.domain.voucher;

import com.devcourse.voucher.DiscountPolicy;
import com.devcourse.voucher.FixedAmountPolicy;
import com.devcourse.voucher.Voucher;
import com.devcourse.voucher.VoucherStatus;
import com.devcourse.voucher.VoucherValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherValidatorTest {
    private final VoucherValidator voucherValidator = new VoucherValidator();
    private final LocalDateTime expiredAt = LocalDateTime.of(2022, 1, 1, 0, 0);

    @Test
    @DisplayName("할인량은 0보다 커야한다.")
    void validateDiscountAmountTest() {
        // given
        int discountAmount = -1;

        // when, then
        assertThatThrownBy(() -> voucherValidator.validateDiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("할인률은 0보다 크고 100보다 작거나 같아야 한다.")
    @ValueSource(ints = {-1, 101})
    void validateDiscountRateTest(int discountRate) {
        assertThatThrownBy(() -> voucherValidator.validateDiscountRate(discountRate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("바우처 만료일은 현재보다 과거일 수 없다.")
    void validateExpirationTest() {
        assertThatThrownBy(() -> voucherValidator.validateExpiration(expiredAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("바우처 검증 테스트")
    class voucherValidateTest {
        private final int discount = 50;

        @Test
        @DisplayName("유효기간이 지난 바우처는 사용할 수 없다.")
        void expiredVoucherTest() {
            // given
            Voucher voucher = Voucher.percent(discount, expiredAt);

            // when, then
            assertThatThrownBy(() -> voucherValidator.validate(voucher))
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("이미 사용한 바우처는 사용할 수 없다.")
        void usedVoucherTest() {
            // given
            UUID id = UUID.randomUUID();
            LocalDateTime expiredAt = LocalDateTime.now();
            DiscountPolicy fixedAmountPolicy = new FixedAmountPolicy(discount);

            Voucher voucher = new Voucher(id, fixedAmountPolicy, expiredAt, VoucherStatus.USED);

            // when, then
            assertThatThrownBy(() -> voucherValidator.validate(voucher))
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}
