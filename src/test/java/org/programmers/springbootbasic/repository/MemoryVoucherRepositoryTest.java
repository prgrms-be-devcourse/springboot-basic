package org.programmers.springbootbasic.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.TestVoucher;
import org.programmers.springbootbasic.voucher.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("메모리 바우처 레포지토리 테스트")
class MemoryVoucherRepositoryTest {

    private static final MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();

    @AfterEach
    void clear() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("저장 기능")
    void insert() {
        Voucher voucher = new TestVoucher(UUID.randomUUID());
        Voucher insertedVoucher = voucherRepository.insert(voucher);
        assertEquals(voucher, insertedVoucher);
    }

    @Test
    @DisplayName("Id로 검색하기")
    void findById() {
        Voucher voucher = new TestVoucher(UUID.randomUUID());
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
        Voucher voucher1 = new TestVoucher(UUID.randomUUID());
        Voucher voucher2 = new TestVoucher(UUID.randomUUID());
        Voucher voucher3 = new TestVoucher(UUID.randomUUID());
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);

        List<Voucher> expected = new ArrayList<>();
        expected.add(voucher1);
        expected.add(voucher2);
        expected.add(voucher3);

        List<Voucher> vouchers = voucherRepository.findAll();

        assertTrue(vouchers.size()==expected.size());
    }
}