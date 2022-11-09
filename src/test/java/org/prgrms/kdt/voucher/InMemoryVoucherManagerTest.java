package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

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
                int size = voucherManager.findAll().size();

                // then
                assertEquals(1, size);
            }

            @Test
            public void 여러개_저장() {
                // given
                Voucher voucher1 = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("0")
                );
                Voucher voucher2 = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("10")
                );
                Voucher voucher3 = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("20")
                );

                // when
                voucherManager.save(voucher1);
                voucherManager.save(voucher2);
                voucherManager.save(voucher3);
                List<Voucher> all = voucherManager.findAll();

                // then
                assertEquals(3, all.size());
            }
        }

        @Nested
        class percent_타입 {
            @BeforeEach
            public void beforeEach() {
                voucherManager = new InMemoryVoucherManager();
            }

            @Test
            public void 값_10() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("10")
                );

                // when
                voucherManager.save(voucher);
                Voucher expectedVoucher = voucherManager.findAll().get(0);

                // then
                assertEquals(expectedVoucher, voucher);
            }

            @Test
            public void 값_20() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("20")
                );

                // when
                voucherManager.save(voucher);
                Voucher expectedVoucher = voucherManager.findAll().get(0);

                // then
                assertEquals(expectedVoucher, voucher);
            }

            @Test
            public void 값_100() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("100")
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
                        VoucherType.PERCENT,
                        new VoucherAmount("0")
                );

                // when
                voucherManager.save(voucher);
                int size = voucherManager.findAll().size();

                // then
                assertEquals(1, size);
            }

            @Test
            public void 여러개_저장() {
                // given
                Voucher voucher1 = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("0")
                );
                Voucher voucher2 = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("10")
                );
                Voucher voucher3 = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("20")
                );

                // when
                voucherManager.save(voucher1);
                voucherManager.save(voucher2);
                voucherManager.save(voucher3);
                List<Voucher> all = voucherManager.findAll();

                // then
                assertEquals(3, all.size());
            }
        }
    }

    @Nested
    class 리스트업_테스트 {
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
                List<Voucher> allVouchers = voucherManager.findAll();

                // then
                assertEquals(allVouchers, List.of(voucher));
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
                List<Voucher> allVouchers = voucherManager.findAll();

                // then
                assertEquals(allVouchers, List.of(voucher));
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
                List<Voucher> allVouchers = voucherManager.findAll();

                // then
                assertEquals(allVouchers, List.of(voucher));
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
                List<Voucher> allVouchers = voucherManager.findAll();

                // then
                assertEquals(allVouchers, List.of(voucher));
            }

            @Test
            public void 여러개_저장() {
                // given
                Voucher voucher1 = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("0")
                );
                Voucher voucher2 = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("10")
                );
                Voucher voucher3 = new Voucher(
                        VoucherType.FIXED,
                        new VoucherAmount("20")
                );

                // when
                voucherManager.save(voucher1);
                voucherManager.save(voucher2);
                voucherManager.save(voucher3);
                List<Voucher> allVouchers = voucherManager.findAll();

                // then
                assertEquals(allVouchers, List.of(voucher1, voucher2, voucher3));
            }
        }

        @Nested
        class percent_타입 {
            @BeforeEach
            public void beforeEach() {
                voucherManager = new InMemoryVoucherManager();
            }

            @Test
            public void 값_10() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("10")
                );

                // when
                voucherManager.save(voucher);
                List<Voucher> allVouchers = voucherManager.findAll();

                // then
                assertEquals(allVouchers, List.of(voucher));
            }

            @Test
            public void 값_20() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("20")
                );

                // when
                voucherManager.save(voucher);
                List<Voucher> allVouchers = voucherManager.findAll();

                // then
                assertEquals(allVouchers, List.of(voucher));
            }

            @Test
            public void 값_100() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("100")
                );

                // when
                voucherManager.save(voucher);
                List<Voucher> allVouchers = voucherManager.findAll();

                // then
                assertEquals(allVouchers, List.of(voucher));
            }

            @Test
            public void 값_0() {
                // given
                Voucher voucher = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("0")
                );

                // when
                voucherManager.save(voucher);
                List<Voucher> allVouchers = voucherManager.findAll();

                // then
                assertEquals(allVouchers, List.of(voucher));
            }

            @Test
            public void 여러개_저장() {
                // given
                Voucher voucher1 = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("0")
                );
                Voucher voucher2 = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("10")
                );
                Voucher voucher3 = new Voucher(
                        VoucherType.PERCENT,
                        new VoucherAmount("20")
                );

                // when
                voucherManager.save(voucher1);
                voucherManager.save(voucher2);
                voucherManager.save(voucher3);
                List<Voucher> allVouchers = voucherManager.findAll();

                // then
                assertEquals(allVouchers, List.of(voucher1, voucher2, voucher3));
            }
        }
    }

}