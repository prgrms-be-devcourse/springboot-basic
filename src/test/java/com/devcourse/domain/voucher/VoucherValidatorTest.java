package com.devcourse.domain.voucher;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.domain.DiscountPolicy;
import com.devcourse.voucher.domain.FixedAmountPolicy;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.application.VoucherValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.devcourse.voucher.domain.VoucherStatus.*;
import static com.devcourse.voucher.domain.VoucherType.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherValidatorTest {
    private final VoucherValidator voucherValidator = new VoucherValidator();
    private final LocalDateTime invalideExpiration = LocalDateTime.of(2022, 1, 1, 0, 0);

    @Nested
    @DisplayName("바우처 생성 유효성 테스트")
    class creationValidationTest {
        private final LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);
        @Test
        @DisplayName("할인량은 0보다 커야한다.")
        void validateDiscountAmountTest() {
            // given
            String voucherSymbol = FIXED.getSymbol();
            int discountAmount = -1;
            CreateVoucherRequest request = new CreateVoucherRequest(voucherSymbol, discountAmount, expiredAt);

            // when, then
            assertThatThrownBy(() -> voucherValidator.validateRequest(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @DisplayName("할인률은 0보다 크고 100보다 작거나 같아야 한다.")
        @ValueSource(ints = {-1, 101})
        void validateDiscountRateTest(int discountRate) {
            // given
            String voucherSymbol = PERCENT.getSymbol();
            CreateVoucherRequest request = new CreateVoucherRequest(voucherSymbol, discountRate, expiredAt);

            // when, then
            assertThatThrownBy(() -> voucherValidator.validateRequest(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("바우처 만료일은 현재보다 과거일 수 없다.")
        void validateExpirationTest() {
            // given
            String voucherSymbol = FIXED.getSymbol();
            int discountAmount = 1_500;
            CreateVoucherRequest request = new CreateVoucherRequest(voucherSymbol, discountAmount, invalideExpiration);

            // when, then
            assertThatThrownBy(() -> voucherValidator.validateRequest(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("바우처 검증 테스트")
    class voucherValidateTest {
        private final int discount = 50;

        @Test
        @DisplayName("유효기간이 지난 바우처는 사용할 수 없다.")
        void expiredVoucherTest() {
            // given
            Voucher voucher = Voucher.percent(discount, invalideExpiration);

            // when, then
            assertThatThrownBy(() -> voucherValidator.isUsable(voucher))
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("이미 사용한 바우처는 사용할 수 없다.")
        void usedVoucherTest() {
            // given
            LocalDateTime expiredAt = LocalDateTime.now();
            DiscountPolicy fixedAmountPolicy = new FixedAmountPolicy(discount);

            Voucher voucher = new Voucher(UUID.randomUUID(), fixedAmountPolicy, expiredAt, USED);

            // when, then
            assertThatThrownBy(() -> voucherValidator.isUsable(voucher))
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}
