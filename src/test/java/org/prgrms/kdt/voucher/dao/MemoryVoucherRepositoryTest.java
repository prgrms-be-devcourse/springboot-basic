package org.prgrms.kdt.voucher.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.FileAccessException;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRepositoryTest {
    MemoryVoucherRepository memoryVoucherRepository;

    @BeforeEach
    void setup() {
        memoryVoucherRepository = new MemoryVoucherRepository();
    }

    @Test
    @DisplayName("존재하는 바우처Id로 바우처 찾기")
    void findByExistId() {
        //given
        Voucher savedVoucher = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        memoryVoucherRepository.insert(savedVoucher);
        UUID existVoucherId = savedVoucher.getVoucherId();

        //when
        Optional<Voucher> foundVoucher = memoryVoucherRepository.findById(existVoucherId);

        //then
        assertThat(foundVoucher.get(), is(savedVoucher));
    }

    @Test
    @DisplayName("존재하지 않는 바우처Id로 바우처 찾기")
    void findByNonExistId() {
        //given
        Voucher savedVoucher = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        memoryVoucherRepository.insert(savedVoucher);
        UUID notExistVoucherId = UUID.randomUUID();

        //when
        Optional<Voucher> foundVoucher = memoryVoucherRepository.findById(notExistVoucherId);

        //then
        assertThrows(FileAccessException.class, () -> {
            foundVoucher.orElseThrow(FileAccessException::new);
        });
    }

    @Test
    @DisplayName("바우처 저장 후 성공적으로 저장 되었는지 확인")
    void insert() {
        //given
        Voucher insertVoucher = new Voucher(VoucherType.PERCENT, VoucherType.PERCENT.createPolicy(30.0));

        //when
        memoryVoucherRepository.insert(insertVoucher);

        //then
        Optional<Voucher> foundVoucher = memoryVoucherRepository.findById(insertVoucher.getVoucherId());
        assertThat(foundVoucher.get(), is(insertVoucher));
    }

    @Test
    @DisplayName("바우처 전체 조회 테스트")
    void findAll() {
        //given
        Voucher savedVoucher1 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        Voucher savedVoucher2 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        memoryVoucherRepository.insert(savedVoucher1);
        memoryVoucherRepository.insert(savedVoucher2);

        //when
        List<Voucher> foundVoucherList = memoryVoucherRepository.findAll();

        //then
        assertThat(foundVoucherList, containsInAnyOrder(savedVoucher1, savedVoucher2));
    }
}