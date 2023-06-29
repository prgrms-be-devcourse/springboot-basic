package org.prgrms.kdt.voucher.repository;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.DatabaseException;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class FileVoucherRepositoryTest {
    public FileVoucherRepository fileVoucherRepository;

    @BeforeEach
    void setup() {
        fileVoucherRepository = new FileVoucherRepository();
    }

    @Test
    void findById_존재하는_바우처_찾기() {
        //given
        Voucher savedVoucher1 = new FixedAmountVoucher(UUID.randomUUID());
        Voucher savedVoucher2 = new FixedAmountVoucher(UUID.randomUUID());
        fileVoucherRepository.insert(savedVoucher1);
        fileVoucherRepository.insert(savedVoucher2);
        UUID existVoucherId = savedVoucher1.getVoucherId();

        //when
        Optional<Voucher> foundVoucher = fileVoucherRepository.findById(existVoucherId);

        //then
        assertThat(foundVoucher.get(), Matchers.is(savedVoucher1));
    }

    @Test
    void findById_존재하지_않는_바우처_찾기() {
        //given
        Voucher savedVoucher = new FixedAmountVoucher(UUID.randomUUID());
        fileVoucherRepository.insert(savedVoucher);
        UUID notExistVoucherId = UUID.randomUUID();

        //when
        Optional<Voucher> foundVoucher = fileVoucherRepository.findById(notExistVoucherId);

        //then
        assertThrows(DatabaseException.class, () -> {
            foundVoucher.orElseThrow(DatabaseException::new);
        });
    }

    @Test
    void insert() {
        //given
        Voucher insertVoucher = new FixedAmountVoucher(UUID.randomUUID());

        //when
        fileVoucherRepository.insert(insertVoucher);

        //then
        Optional<Voucher> foundVoucher = fileVoucherRepository.findById(insertVoucher.getVoucherId());
        assertThat(foundVoucher.get(), is(insertVoucher));
    }

    @Test
    void findAll() {
        //given
        Voucher savedVoucher1 = new FixedAmountVoucher(UUID.randomUUID());
        Voucher savedVoucher2 = new FixedAmountVoucher(UUID.randomUUID());
        fileVoucherRepository.insert(savedVoucher1);
        fileVoucherRepository.insert(savedVoucher2);

        //when
        List<Voucher> foundVoucherList = fileVoucherRepository.findAll();

        //then
        assertThat(foundVoucherList, containsInAnyOrder(savedVoucher1, savedVoucher2));
    }
}