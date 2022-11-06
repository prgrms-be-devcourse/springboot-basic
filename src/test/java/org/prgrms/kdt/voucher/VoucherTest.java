package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.utils.VoucherValidator;

class VoucherTest {

    private final VoucherValidator voucherValidator = new VoucherValidator();

    @Nested
    class 바우처_값_테스트 {
        @Nested
        class fixed_타입 {
            @Test
            void 값_10_생성() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("10")
                );

                // when
                voucher.validate(voucherValidator);

                // then
            }

            @Test
            void 값_1000_생성() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("1000")
                );

                // when
                voucher.validate(voucherValidator);

                // then
            }

            @Test
            void 값_실수형_생성_예외_발생() {
                // given

                // when
                Assertions.assertThrows(NumberFormatException.class, () -> new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("10.03")
                ));

                // then
            }

            @Test
            void 값_음수_생성_예외_발생() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("-10")
                );

                // when
                Assertions.assertThrows(NumberFormatException.class, () -> voucher.validate(voucherValidator));

                // then
            }
        }

        @Nested
        class percent_타입 {
            @Test
            void 값_10_생성() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("10")
                );

                // when
                voucher.validate(voucherValidator);

                // then
            }

            @Test
            void 값_1000_생성_예외_발생() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("1000")
                );

                // when
                Assertions.assertThrows(NumberFormatException.class, () -> voucher.validate(voucherValidator));

                // then
            }

            @Test
            void 값_실수형_생성_예외_발생() {
                // given

                // when
                Assertions.assertThrows(NumberFormatException.class, () -> new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("10.03")
                ));

                // then
            }

            @Test
            void 값_음수_생성_예외_발생() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("-100")
                );

                // when
                Assertions.assertThrows(NumberFormatException.class, () -> voucher.validate(voucherValidator));

                // then
            }
        }

    }

}