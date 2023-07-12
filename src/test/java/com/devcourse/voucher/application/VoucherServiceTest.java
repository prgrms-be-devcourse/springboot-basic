package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.domain.repository.MemoryVoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;

import static com.devcourse.voucher.domain.Voucher.Type.FIXED;
import static com.devcourse.voucher.domain.Voucher.Type.PERCENT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(classes = {VoucherService.class, MemoryVoucherRepository.class})
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
            CreateVoucherRequest request = new CreateVoucherRequest(discountAmount, expiredAt, FIXED);

            // when, then
            assertThatThrownBy(() -> voucherService.create(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @DisplayName("0보다 작거나 100보다 큰 할인률을 받으면 예외가 발생한다.")
        @ValueSource(ints = {-1, 101})
        void validateDiscountRateTest(int discountRate) {
            // given
            CreateVoucherRequest request = new CreateVoucherRequest(discountRate, expiredAt, PERCENT);

            // when, then
            assertThatThrownBy(() -> voucherService.create(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("현재보다 과거인 만료일을 받으면 예외가 발생한다.")
        void validateExpirationTest() {
            // given
            int discountAmount = 1_500;
            CreateVoucherRequest request = new CreateVoucherRequest(discountAmount, invalidExpiration, FIXED);

            // when, then
            assertThatThrownBy(() -> voucherService.create(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
