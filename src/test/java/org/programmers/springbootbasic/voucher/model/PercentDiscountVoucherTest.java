package org.programmers.springbootbasic.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.application.voucher.model.PercentDiscountVoucher;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PercentDiscountVoucher 클래스")
class PercentDiscountVoucherTest {

    @Nested
    @DisplayName("discount 메소드는")
    class Discount_of {
        private final PercentDiscountVoucher fixedAmountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10, LocalDateTime.now());

        @Nested
        @DisplayName("percent가 할인 가능한 값일때.")
        class Context_with_discount_able {

            @Test
            @DisplayName("입력 값에서 percent만큼 할인된 값을 반환합니다.")
            void it_returns_a_discount_value() {
                assertThat(fixedAmountVoucher.discount(1000)).isEqualTo(900);
            }
        }
    }

    @Nested
    @DisplayName("바우처를 생성할 때")
    class Context_with_create_voucher {

        @Nested
        @DisplayName("percent가 0보다 크고 100보다 작다면")
        class Context_with_able_amount{
            private final PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10, LocalDateTime.now());

            @Test
            @DisplayName("생성에 성공합니다.")
            void it_returns_true() {
                assertThat(new PercentDiscountVoucher(percentDiscountVoucher.getVoucherId(), 10, LocalDateTime.now()).getVoucherId()).isEqualTo(percentDiscountVoucher.getVoucherId());
            }
        }

        @Nested
        @DisplayName("percent가 음수값이라면")
        class Context_with_minus {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> {
                    new PercentDiscountVoucher(UUID.randomUUID(), -100, LocalDateTime.now());
                })
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("할인율은 0 초과 100 이하여야 합니다.");
            }
        }
        @Nested
        @DisplayName("percent가 99보다 큰 값이라면")
        class Context_with_more_than_max_amount {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> {
                    new PercentDiscountVoucher(UUID.randomUUID(), 1000000, LocalDateTime.now());
                })
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("할인율은 0 초과 100 이하여야 합니다.");
            }
        }
    }
}