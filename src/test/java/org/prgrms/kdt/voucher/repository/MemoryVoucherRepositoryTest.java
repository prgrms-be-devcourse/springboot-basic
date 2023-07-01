package org.prgrms.kdt.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.DatabaseException;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRepositoryTest {
    public MemoryVoucherRepository memoryVoucherRepository;

    @BeforeEach
    public void setup() {
        memoryVoucherRepository = new MemoryVoucherRepository();
    }

    @Test
    void findById_존재하는_바우처_조회() {
        //given
        Voucher savedVoucher = new FixedAmountVoucher(UUID.randomUUID());
        memoryVoucherRepository.insert(savedVoucher);
        UUID existVoucherId = savedVoucher.getVoucherId();

        //when
        Optional<Voucher> foundVoucher = memoryVoucherRepository.findById(existVoucherId);

        //then
        assertThat(foundVoucher.get(), is(savedVoucher));
    }

    @Test
    void findById_존재하지_않는_바우처_조회() {
        //given
        Voucher savedVoucher = new FixedAmountVoucher(UUID.randomUUID());
        memoryVoucherRepository.insert(savedVoucher);
        UUID notExistVoucherId = UUID.randomUUID();

        //when
        Optional<Voucher> foundVoucher = memoryVoucherRepository.findById(notExistVoucherId);

        //then
        assertThrows(DatabaseException.class, () -> {
            foundVoucher.orElseThrow(DatabaseException::new);
        });
    }

    @Test
    void insert() {
        //given
        Voucher insertVoucher = new PercentDiscountVoucher(UUID.randomUUID());

        //when
        memoryVoucherRepository.insert(insertVoucher);

        //then
        Optional<Voucher> foundVoucher = memoryVoucherRepository.findById(insertVoucher.getVoucherId());
        assertThat(foundVoucher.get(), is(insertVoucher));
    }

    @Test
    void findAll() {
        //given
        Voucher savedVoucher1 = new FixedAmountVoucher(UUID.randomUUID());
        Voucher savedVoucher2 = new FixedAmountVoucher(UUID.randomUUID());
        memoryVoucherRepository.insert(savedVoucher1);
        memoryVoucherRepository.insert(savedVoucher2);

        //when
        List<Voucher> foundVoucherList = memoryVoucherRepository.findAll();

        //then
        assertThat(foundVoucherList, containsInAnyOrder(savedVoucher1, savedVoucher2));
    }
}