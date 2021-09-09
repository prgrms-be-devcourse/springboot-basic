package org.prgrms.kdt.kdtspringorder.voucher.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PercentDiscountVoucher 단위 테스트")
class PercentDiscountVoucherTest {

    @Nested
    @DisplayName("생성자는")
    class Describe_Constructor {

        @Nested
        @DisplayName("할인 퍼센티지가 0보다 작게 입력된다면")
        class Context_discount_minus{

            private final long DISCOUNT_INPUT = -100;

            @Test
            @DisplayName("IllegalArgumentException을 던집니다.")
            void it_return_() {
                assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), DISCOUNT_INPUT));
            }

        }

        @Nested
        @DisplayName("할인 퍼센티지가 100보다 크게 입력된다면")
        class Context_discount_over_max{

            private final long DISCOUNT_INPUT = 110;

            @Test
            @DisplayName("IllegalArgumentException을 던집니다.")
            void it_return_() {
                assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), DISCOUNT_INPUT));
            }

        }

        @Nested
        @DisplayName("할인가격이 0보다 크고 10000보다 작게 입력된다면")
        class Context_discount_plus{

            private final long DISCOUNT_INPUT = 100;
            private PercentDiscountVoucher voucher;

            @BeforeEach
            void setUp() {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), DISCOUNT_INPUT);
            }

            @Test
            @DisplayName("PercentDiscountVoucher 인스턴스를 생성합니다.")
            void it_return_() {
                assertNotNull(voucher);
                assertThat(voucher.getAmount(), equalTo(DISCOUNT_INPUT));
            }

        }

    }

    @Nested
    @DisplayName("discount 메서드는")
    class Describe_discount {

        private final long DISCOUNT_INPUT = 100;
        private PercentDiscountVoucher voucher;

        @BeforeEach
        void setUp() {
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), DISCOUNT_INPUT);
        }

        @Nested
        @DisplayName("할인을 적용할 금액을 인자로 받으면")
        class Context_input_percent{

            private long BEFORE_DISCOUNT = 10;

            @Test
            @DisplayName("할인 퍼센티지 만큼 할인된 금액을 반환합니다.")
            void it_return_() {
                final long afterDiscount = voucher.discount(BEFORE_DISCOUNT);
                assertThat(afterDiscount, is(BEFORE_DISCOUNT * (voucher.getAmount())/100));
            }

        }

    }

}
