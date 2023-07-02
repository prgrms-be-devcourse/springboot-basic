package org.prgrms.kdt.voucher.dao;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.FileAccessException;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FileVoucherRepositoryTest {
    private FileVoucherRepository fileVoucherRepository;
    private VoucherLoader mockVoucherLoader;
    @BeforeEach
    void setup() {
        mockVoucherLoader = mock(VoucherLoader.class);
        fileVoucherRepository = new FileVoucherRepository(mockVoucherLoader);
    }

    @Test
    void findById_존재하는_바우처_찾기() {
        //given
        Voucher savedVoucher1 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        Voucher savedVoucher2 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(20.0));
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
        Voucher savedVoucher = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        fileVoucherRepository.insert(savedVoucher);
        UUID notExistVoucherId = UUID.randomUUID();

        //when
        Optional<Voucher> foundVoucher = fileVoucherRepository.findById(notExistVoucherId);

        //then
        assertThrows(FileAccessException.class, () -> {
            foundVoucher.orElseThrow(FileAccessException::new);
        });
    }

    @Test
    void insert() {
        //given
        Voucher insertVoucher = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));

        //when
        fileVoucherRepository.insert(insertVoucher);

        //then
        Optional<Voucher> foundVoucher = fileVoucherRepository.findById(insertVoucher.getVoucherId());
        assertThat(foundVoucher.get(), is(insertVoucher));
    }

    @Test
    void findAll() {
        //given
        Voucher savedVoucher1 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        Voucher savedVoucher2 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        fileVoucherRepository.insert(savedVoucher1);
        fileVoucherRepository.insert(savedVoucher2);

        //when
        List<Voucher> foundVoucherList = fileVoucherRepository.findAll();

        //then
        assertThat(foundVoucherList, containsInAnyOrder(savedVoucher1, savedVoucher2));
    }
}