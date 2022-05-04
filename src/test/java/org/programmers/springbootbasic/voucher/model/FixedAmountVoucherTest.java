package org.programmers.springbootbasic.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("FixedAmountVoucher 클래스")
class FixedAmountVoucherTest {

    @Nested
    @DisplayName("discount 메소드는")
    class DiscountOf {
        private final FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());

        @Nested
        @DisplayName("amount가 할인 가능한 값일때.")
        class ContextWithDiscountAble {

            @Test
            @DisplayName("입력 값에서 amount만큼 할인된 값을 반환합니다.")
            void it_returns_a_discount_value() {
                assertThat(fixedAmountVoucher.discount(1000)).isEqualTo(900);
            }
        }

        @Nested
        @DisplayName("amount가 할인 가능한 값보다 클 때")
        class ContextWithDiscountUnable {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> {
                    fixedAmountVoucher.discount(90);
                }).isInstanceOf(ArithmeticException.class)
                        .hasMessageContaining("결제 금액보다 할인 금액이 더 큽니다.");
            }
        }
    }

    @Nested
    @DisplayName("바우처를 생성할 때")
    class ContextWithCreateVoucher {

        @Nested
        @DisplayName("amount가 양수값이고 MAX_AMOUNT보다 작다면")
        class ContextWithAbleAmount{
            private final FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());

            @Test
            @DisplayName("생성에 성공합니다.")
            void it_returns_true() {
                assertThat(new FixedAmountVoucher(fixedAmountVoucher.getVoucherId(), 100, LocalDateTime.now()).getVoucherId()).isEqualTo(fixedAmountVoucher.getVoucherId());
            }
        }

        @Nested
        @DisplayName("amount가 음수값이라면")
        class ContextWithMinus {

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> {
                    new FixedAmountVoucher(UUID.randomUUID(), -100, LocalDateTime.now());
                })
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("할인된 금액은 0보다 커야 합니다.");
            }
        }
        @Nested
        @DisplayName("amount가 MAX_AMOUNT보다 큰 값이라면")
        class ContextWithMoreThanMaxAmount {
            private static final long MAX_VOUCHER_AMOUNT = 10000;

            @Test
            @DisplayName("예외를 던집니다.")
            void it_returns_a_throw() {
                assertThatThrownBy(() -> {
                    new FixedAmountVoucher(UUID.randomUUID(), 1000000, LocalDateTime.now());
                })
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("할인금액은 %d보다 작아야 합니다.".formatted(MAX_VOUCHER_AMOUNT));
            }
        }
    }
}