package org.prgrms.kdt.voucher.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherAmount;
import org.prgrms.kdt.voucher.VoucherMetaData;
import org.prgrms.kdt.voucher.VoucherType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherMapperTest {

    private final VoucherMapper voucherMapper = new VoucherMapper();

    @Nested
    class 메타_데이터에서_Voucher로_매핑테스트 {
        @Nested
        class fixed_타입 {
            @Test
            public void 값_10() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData(
                        "fixed",
                        "10"
                );
                Voucher expectedVoucher = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("10")
                );

                // when
                Voucher resultVoucher = voucherMapper.MetaDataToVoucher(voucherMetaData);

                // then
                assertEquals(expectedVoucher, resultVoucher);
            }

            @Test
            public void 값_1000() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData(
                        "fixed",
                        "1000"
                );
                Voucher expectedVoucher = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("1000")
                );

                // when
                Voucher resultVoucher = voucherMapper.MetaDataToVoucher(voucherMetaData);

                // then
                assertEquals(expectedVoucher, resultVoucher);
            }

            @Test
            public void 값_실수형_예외_발생() {
                // given

                // when
                assertThrows(NumberFormatException.class, () -> new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("10.2")
                ));

                // then
            }

            @Test
            public void 값_음수() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData(
                        "fixed",
                        "-10"
                );
                Voucher expectedVoucher = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("-10")
                );

                // when
                Voucher resultVoucher = voucherMapper.MetaDataToVoucher(voucherMetaData);

                // then
                assertEquals(expectedVoucher, resultVoucher);
            }
        }

        @Nested
        class percent_타입 {
            @Test
            public void 값_10() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData(
                        "percent",
                        "10"
                );
                Voucher expectedVoucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("10")
                );

                // when
                Voucher resultVoucher = voucherMapper.MetaDataToVoucher(voucherMetaData);

                // then
                assertEquals(expectedVoucher, resultVoucher);
            }

            @Test
            public void 값_1000() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData(
                        "percent",
                        "1000"
                );
                Voucher expectedVoucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("1000")
                );

                // when
                Voucher resultVoucher = voucherMapper.MetaDataToVoucher(voucherMetaData);

                // then
                assertEquals(expectedVoucher, resultVoucher);
            }

            @Test
            public void 값_실수형_예외_발생() {
                // given

                // when
                assertThrows(NumberFormatException.class, () -> new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("10.2")
                ));

                // then
            }

            @Test
            public void 값_음수() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData(
                        "percent",
                        "-10"
                );
                Voucher expectedVoucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("-10")
                );

                // when
                Voucher resultVoucher = voucherMapper.MetaDataToVoucher(voucherMetaData);

                // then
                assertEquals(expectedVoucher, resultVoucher);
            }
        }
    }
}