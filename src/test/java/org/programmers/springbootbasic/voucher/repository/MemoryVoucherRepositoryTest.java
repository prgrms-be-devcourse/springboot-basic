package org.programmers.springbootbasic.voucher.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("메모리 바우처 레포지토리 테스트")
class MemoryVoucherRepositoryTest {

    private static final MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();

    private static final Voucher voucher = new FixedDiscountVoucher(UUID.randomUUID(), 2000);
    private static final Voucher voucher2 = new FixedDiscountVoucher(UUID.randomUUID(), 1000);
    private static final Voucher voucher3 = new FixedDiscountVoucher(UUID.randomUUID(), 1500);

    @AfterEach
    void clear() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("저장 기능")
    void insert() {
        Voucher insertedVoucher = voucherRepository.insert(voucher);
        assertEquals(voucher, insertedVoucher);
    }

    @Test
    @DisplayName("Id로 검색하기")
    void findById() {
        voucherRepository.insert(voucher);

        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getId());
        assertEquals(voucher, foundVoucher.get());
    }

    @Test
    @DisplayName("Id로 검색하기 - 결과 없음")
    void findByIdWithNoResult() {
        Optional<Voucher> foundVoucher = voucherRepository.findById(UUID.randomUUID());
        assertTrue(foundVoucher.isEmpty());
    }

    @Test
    @DisplayName("전체 검색")
    void findAll() {
        voucherRepository.insert(voucher);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);

        List<Voucher> expected = new ArrayList<>();
        expected.add(voucher);
        expected.add(voucher2);
        expected.add(voucher3);

        List<Voucher> vouchers = voucherRepository.findAll();

        assertTrue(vouchers.size() == expected.size());
    }
}