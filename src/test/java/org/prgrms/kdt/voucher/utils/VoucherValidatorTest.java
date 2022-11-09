package org.prgrms.kdt.voucher.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.VoucherAmount;
import org.prgrms.kdt.voucher.VoucherType;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherValidatorTest {
    private VoucherValidator voucherValidator = new VoucherValidator();

    @Nested
    class 바우처_값_Validation_테스트 {
        @Nested
        class fixed_타입 {
            @Test
            void 값_10() {
                // given
                VoucherAmount voucherAmount = new VoucherAmount("10");

                // when
                voucherValidator.validateAmount(VoucherType.FIXED, voucherAmount);

                // then
            }

            @Test
            void 값_1000() {
                // given
                VoucherAmount voucherAmount = new VoucherAmount("1000");

                // when
                voucherValidator.validateAmount(VoucherType.FIXED, voucherAmount);

                // then
            }

            @Test
            void 값_실수형_예외_발생() {
                // given

                // when
                assertThrows(NumberFormatException.class, () -> new VoucherAmount("10.03"));

                // then
            }

            @Test
            void 값_음수_예외_발생() {
                // given
                VoucherAmount voucherAmount = new VoucherAmount("-10");

                // when
                assertThrows(NumberFormatException.class, () -> voucherValidator.validateAmount(VoucherType.PERCENT, voucherAmount));

                // then
            }
        }

        @Nested
        class percent_타입 {
            @Test
            void 값_10() {
                // given
                VoucherAmount voucherAmount = new VoucherAmount("10");

                // when
                voucherValidator.validateAmount(VoucherType.FIXED, voucherAmount);

                // then
            }

            @Test
            void 값_1000_예외_발생() {
                // given
                VoucherAmount voucherAmount = new VoucherAmount("1000");

                // when
                assertThrows(NumberFormatException.class, () -> voucherValidator.validateAmount(VoucherType.PERCENT, voucherAmount));

                // then
            }

            @Test
            void 값_실수형_예외_발생() {
                // given

                // when
                assertThrows(NumberFormatException.class, () -> new VoucherAmount("10.03"));

                // then
            }

            @Test
            void 값_음수_예외_발생() {
                // given
                VoucherAmount voucherAmount = new VoucherAmount("-10");

                // when
                assertThrows(NumberFormatException.class, () -> voucherValidator.validateAmount(VoucherType.PERCENT, voucherAmount));

                // then
            }
        }

    }
}