package org.prgrms.kdt.voucher.dao;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.exception.FileAccessException;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FileVoucherRepositoryTest {
    FileVoucherRepository fileVoucherRepository;
    VoucherLoader mockVoucherLoader;

    @BeforeEach
    void setup() {
        mockVoucherLoader = mock(VoucherLoader.class);
        fileVoucherRepository = new FileVoucherRepository(mockVoucherLoader);
    }

    @ParameterizedTest
    @MethodSource("voucherSource")
    @DisplayName("존재하는 바우처Id로 바우처 찾기")
    void findByExistId(Voucher savedVoucher1, Voucher savedVoucher2) {
        //given
        fileVoucherRepository.insert(savedVoucher1);
        fileVoucherRepository.insert(savedVoucher2);
        UUID existVoucherId = savedVoucher1.getVoucherId();

        //when
        Optional<Voucher> foundVoucher = fileVoucherRepository.findById(existVoucherId);

        //then
        assertThat(foundVoucher.get(), Matchers.is(savedVoucher1));
    }

    @Test
    @DisplayName("존재하지 않는 바우처Id로 바우처 찾기")
    void findByNonExistId() {
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
    @DisplayName("바우처 저장 후 성공적으로 저장 되었는지 확인")
    void insert() {
        //given
        Voucher insertVoucher = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));

        //when
        fileVoucherRepository.insert(insertVoucher);

        //then
        Optional<Voucher> foundVoucher = fileVoucherRepository.findById(insertVoucher.getVoucherId());
        assertThat(foundVoucher.get(), is(insertVoucher));
    }

    @ParameterizedTest
    @MethodSource("voucherSource")
    @DisplayName("바우처 전체 조회 테스트")
    void findAll(Voucher savedVoucher1, Voucher savedVoucher2) {
        //given
        fileVoucherRepository.insert(savedVoucher1);
        fileVoucherRepository.insert(savedVoucher2);

        //when
        List<Voucher> foundVoucherList = fileVoucherRepository.findAll();

        //then
        assertThat(foundVoucherList, containsInAnyOrder(savedVoucher1, savedVoucher2));
    }

    static Stream<Voucher[]> voucherSource() {
        Voucher voucher1 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        Voucher voucher2 = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));
        return Stream.of(new Voucher[][]{{voucher1, voucher2}});
    }
}