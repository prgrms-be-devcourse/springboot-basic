package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryVoucherManagerTest {

    private VoucherManager voucherManager;

    @Nested
    class 저장_테스트 {

        @Nested
        class fixed_타입 {

            @BeforeEach
            public void beforeEach() {
                voucherManager = new InMemoryVoucherManager();
            }

            @Test
            public void 값_10() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("10")
                );

                // when
                voucherManager.save(voucher);
                Voucher expectedVoucher = voucherManager.findAll().get(0);

                // then
                assertEquals(expectedVoucher, voucher);
            }

            @Test
            public void 값_1000() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("1000")
                );

                // when
                voucherManager.save(voucher);
                Voucher expectedVoucher = voucherManager.findAll().get(0);

                // then
                assertEquals(expectedVoucher, voucher);
            }

            @Test
            public void 값_100000() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("100000")
                );

                // when
                voucherManager.save(voucher);
                Voucher expectedVoucher = voucherManager.findAll().get(0);

                // then
                assertEquals(expectedVoucher, voucher);
            }

            @Test
            public void 값_0() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("0")
                );

                // when
                voucherManager.save(voucher);
                Voucher expectedVoucher = voucherManager.findAll().get(0);

                // then
                assertEquals(expectedVoucher, voucher);
            }
        }

        @Nested
        class percent_타입 {

        }
    }
}