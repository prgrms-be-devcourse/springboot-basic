package com.devcourse.voucher.application;

import com.devcourse.global.configuration.AppConfig;
import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.domain.DiscountPolicy;
import com.devcourse.voucher.domain.FixedAmountPolicy;
import com.devcourse.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.devcourse.voucher.domain.VoucherType.FIXED;
import static com.devcourse.voucher.domain.VoucherType.PERCENT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(classes = {VoucherService.class, AppConfig.class})
class VoucherServiceTest {
    @Autowired
    private VoucherService voucherService;
    private final LocalDateTime invalidExpiration = LocalDateTime.of(2022, 1, 1, 0, 0);

    @Nested
    @DisplayName("바우처 생성 유효성 테스트")
    class creationValidationTest {
        private final LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);

        @Test
        @DisplayName("0보다 작은 할인량을 받으면 예외가 발생한다..")
        void validateDiscountAmountTest() {
            // given
            int discountAmount = -1;
            CreateVoucherRequest request = new CreateVoucherRequest(FIXED, discountAmount, expiredAt);

            // when, then
            assertThatThrownBy(() -> voucherService.validateRequest(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @DisplayName("0보다 작거나 100보다 큰 할인률을 받으면 예외가 발생한다.")
        @ValueSource(ints = {-1, 101})
        void validateDiscountRateTest(int discountRate) {
            // given
            CreateVoucherRequest request = new CreateVoucherRequest(PERCENT, discountRate, expiredAt);

            // when, then
            assertThatThrownBy(() -> voucherService.validateRequest(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("현재보다 과거인 만료일을 받으면 예외가 발생한다.")
        void validateExpirationTest() {
            // given
            int discountAmount = 1_500;
            CreateVoucherRequest request = new CreateVoucherRequest(FIXED, discountAmount, invalidExpiration);

            // when, then
            assertThatThrownBy(() -> voucherService.validateRequest(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("바우처 사용 검증 테스트")
    class voucherValidateTest {
        private final int discount = 50;

        @Test
        @DisplayName("유효기간이 지난 바우처를 사용하면 예외가 발생한다.")
        void expiredVoucherTest() {
            // given
            Voucher voucher = Voucher.percent(discount, invalidExpiration);

            // when, then
            assertThatThrownBy(() -> voucherService.validateUsable(voucher))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("이미 사용한 바우처를 사용하려고 하면 예외가 발생한다.")
        void usedVoucherTest() {
            // given
            LocalDateTime expiredAt = LocalDateTime.now();
            DiscountPolicy fixedAmountPolicy = new FixedAmountPolicy(discount);

            Voucher voucher = new Voucher(UUID.randomUUID(), fixedAmountPolicy, expiredAt);
            voucher.apply(1000L);

            // when, then
            assertThatThrownBy(() -> voucherService.validateUsable(voucher))
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}
