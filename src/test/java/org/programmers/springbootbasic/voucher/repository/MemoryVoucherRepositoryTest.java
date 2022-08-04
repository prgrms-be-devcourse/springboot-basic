package org.programmers.springbootbasic.voucher.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.RateDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.Voucher;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.RATE;

@DisplayName("메모리 바우처 레포지토리 테스트")
class MemoryVoucherRepositoryTest {

    private static final MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();

    private static final Voucher VOUCHER = new FixedDiscountVoucher(UUID.randomUUID(), 2000);
    private static final Voucher VOUCHER_2 = new FixedDiscountVoucher(UUID.randomUUID(), 1000);
    private static final Voucher VOUCHER_3 = new FixedDiscountVoucher(UUID.randomUUID(), 1500);

    @AfterEach
    void clear() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("저장 기능")
    void insert() {
        Voucher insertedVoucher = voucherRepository.insert(VOUCHER);
        assertEquals(VOUCHER, insertedVoucher);
    }

    @Test
    @DisplayName("Id로 검색하기")
    void findById() {
        voucherRepository.insert(VOUCHER);

        Optional<Voucher> foundVoucher = voucherRepository.findById(VOUCHER.getId());
        assertEquals(VOUCHER, foundVoucher.get());
    }

    @Test
    @DisplayName("Id로 검색하기 - 결과 없음")
    void findByIdWithNoResult() {
        Optional<Voucher> foundVoucher = voucherRepository.findById(UUID.randomUUID());
        assertTrue(foundVoucher.isEmpty());
    }

    @Test
    @DisplayName("타입 별로 검색하기")
    void findByType() {
        Voucher rateDiscountVoucher1 = new RateDiscountVoucher(UUID.randomUUID(), 10);
        Voucher rateDiscountVoucher2 = new RateDiscountVoucher(UUID.randomUUID(), 20);

        voucherRepository.insert(VOUCHER);
        voucherRepository.insert(VOUCHER_2);
        voucherRepository.insert(VOUCHER_3);
        voucherRepository.insert(rateDiscountVoucher1);
        voucherRepository.insert(rateDiscountVoucher2);

        List<Voucher> expected = new ArrayList<>();
        expected.add(rateDiscountVoucher1);
        expected.add(rateDiscountVoucher2);

        List<Voucher> vouchers = voucherRepository.findByType(RATE);

        assertEquals(vouchers.size(), expected.size());
    }

    @Test
    @DisplayName("바우처 기간으로 조회")
    void findByDate() {
        var voucher1 = new FixedDiscountVoucher(UUID.randomUUID(), 3000, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 4, 25, 2, 40)));
        var voucher2 = new FixedDiscountVoucher(UUID.randomUUID(), 8000, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 3, 15, 14, 15)));
        var voucher3 = new FixedDiscountVoucher(UUID.randomUUID(), 12000, null,
                Timestamp.valueOf(LocalDateTime.of(2021, 1, 22, 10, 30)));
        var voucher4 = new RateDiscountVoucher(UUID.randomUUID(), 15, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 1, 22, 10, 30)));
        var voucher5 = new RateDiscountVoucher(UUID.randomUUID(), 30, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 3, 22, 10, 30)));
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);
        voucherRepository.insert(voucher4);
        voucherRepository.insert(voucher5);

        List<Voucher> foundVouchers = voucherRepository.findByDate(
                Timestamp.valueOf(LocalDateTime.of(2022, 1, 1, 1, 10)),
                Timestamp.valueOf(LocalDateTime.of(2022, 4, 1, 2, 30)));

        List<Voucher> expected = new ArrayList<>();
        expected.add(voucher2);
        expected.add(voucher4);
        expected.add(voucher5);

        assertThat(foundVouchers.size(), is(expected.size()));
        assertThat(foundVouchers.containsAll(expected), is(true));
    }

    @Test
    @DisplayName("바우처 종류와 기간으로 조회")
    void findByTypeAndDate() {
        var voucher1 = new FixedDiscountVoucher(UUID.randomUUID(), 3000, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 4, 25, 2, 40)));
        var voucher2 = new FixedDiscountVoucher(UUID.randomUUID(), 8000, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 3, 15, 14, 15)));
        var voucher3 = new FixedDiscountVoucher(UUID.randomUUID(), 12000, null,
                Timestamp.valueOf(LocalDateTime.of(2021, 1, 22, 10, 30)));
        var voucher4 = new RateDiscountVoucher(UUID.randomUUID(), 15, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 1, 22, 10, 30)));
        var voucher5 = new RateDiscountVoucher(UUID.randomUUID(), 30, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 3, 22, 10, 30)));
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);
        voucherRepository.insert(voucher4);
        voucherRepository.insert(voucher5);

        List<Voucher> foundVouchers = voucherRepository.findByTypeAndDate(RATE,
                Timestamp.valueOf(LocalDateTime.of(2022, 1, 1, 1, 10)),
                Timestamp.valueOf(LocalDateTime.of(2022, 4, 1, 2, 30)));

        List<Voucher> expected = new ArrayList<>();
        expected.add(voucher4);
        expected.add(voucher5);

        assertThat(foundVouchers.size(), is(expected.size()));
        assertThat(foundVouchers.containsAll(expected), is(true));
    }

    @Test
    @DisplayName("전체 검색")
    void findAll() {
        voucherRepository.insert(VOUCHER);
        voucherRepository.insert(VOUCHER_2);
        voucherRepository.insert(VOUCHER_3);

        List<Voucher> expected = new ArrayList<>();
        expected.add(VOUCHER);
        expected.add(VOUCHER_2);
        expected.add(VOUCHER_3);

        List<Voucher> vouchers = voucherRepository.findAll();

        assertEquals(vouchers.size(), expected.size());
    }
}