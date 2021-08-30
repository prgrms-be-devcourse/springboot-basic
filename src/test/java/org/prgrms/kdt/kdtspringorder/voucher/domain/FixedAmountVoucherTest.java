package org.prgrms.kdt.kdtspringorder.voucher.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@DisplayName("FixedAmountVoucher 단위 테스트")
class FixedAmountVoucherTest {

    @Nested
    @DisplayName("생성자는")
    class Describe_Constructor {

        @Nested
        @DisplayName("할인가격이 0보다 작게 입력된다면")
        class Context_discount_minus{

            private final long DISCOUNT_INPUT = -100;

            @Test
            @DisplayName("IllegalArgumentException을 던집니다.")
            void it_return_() {
                assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), DISCOUNT_INPUT));
            }

        }

        @Nested
        @DisplayName("할인가격이 10000보다 크게 입력된다면")
        class Context_discount_over_max{

            private final long DISCOUNT_INPUT = 1000000000;

            @Test
            @DisplayName("IllegalArgumentException을 던집니다.")
            void it_return_() {
                assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), DISCOUNT_INPUT));
            }

        }

        @Nested
        @DisplayName("할인가격이 0보다 크고 10000보다 작게 입력된다면")
        class Context_discount_plus{

            private final long DISCOUNT_INPUT = 100;
            private FixedAmountVoucher voucher;

            @BeforeEach
            void setUp() {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), DISCOUNT_INPUT);
            }

            @Test
            @DisplayName("FixedAmountVoucher 인스턴스를 생성합니다.")
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
        private FixedAmountVoucher voucher;

        @BeforeEach
        void setUp() {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), DISCOUNT_INPUT);
        }

        @Nested
        @DisplayName("할인금액보다 큰 금액을 인자로 받는다면")
        class Context_input_over_discount_amount{

            private long BEFORE_DISCOUNT = 1000;

            @Test
            @DisplayName("할인된 금액을 반환합니다.")
            void it_return_() {
                final long afterDiscount = voucher.discount(BEFORE_DISCOUNT);
                assertThat(afterDiscount, is(BEFORE_DISCOUNT - voucher.getAmount()));
            }

        }

        @Nested
        @DisplayName("할인금액보다 작은 금액을 인자로 받는다면")
        class Context_input_under_discount_amount{

            private long BEFORE_DISCOUNT = 10;

            @Test
            @DisplayName("0을 반환합니다.")
            void it_return_() {
                final long afterDiscount = voucher.discount(BEFORE_DISCOUNT);
                assertThat(afterDiscount, is(0L));
            }

        }

    }



//    @Test
//    @DisplayName("할인된 금액은 마이너스가 될 수 없다.")
//    void testDiscount() {
//        FixedAmountVoucher sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);
//        assertEquals(0, sut.discount(900));
//    }
//
//    @Test
//    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
////    @Disabled // 테스트를 잠시 사용 안함
//    void testWithMinus() {
//        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
//
//    }
//
//    @Test
//    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
//    void testVoucherCreation() {
//        assertAll("FixedAmountVoucher Creation",
//            ()-> assertThrows(IllegalArgumentException.class, ()-> new FixedAmountVoucher(UUID.randomUUID(), 0)),
//            ()-> assertThrows(IllegalArgumentException.class, ()-> new FixedAmountVoucher(UUID.randomUUID(), -100)),
//            ()-> assertThrows(IllegalArgumentException.class, ()-> new FixedAmountVoucher(UUID.randomUUID(), 10000000))
//        );
//    }

}
