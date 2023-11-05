package org.programmers.springorder.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.model.VoucherType;
import org.programmers.springorder.voucher.repository.MemoryVoucherRepository;
import org.programmers.springorder.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    private VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("바우처 저장에 성공한다.")
    void saveVoucher() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher1 = Voucher.toVoucher(voucherId1, 1000, VoucherType.FIXED);
        UUID voucherId2 = UUID.randomUUID();
        Voucher voucher2 = Voucher.toVoucher(voucherId2, 10, VoucherType.PERCENT);

        // when
        Voucher saveVoucherId1 = voucherRepository.save(voucher1);
        Voucher saveVoucherId2 = voucherRepository.save(voucher2);

        List<Voucher> vouchers = voucherRepository.findAll();

        // then
        assertThat(vouchers).hasSize(2);
        assertThat(voucher1).isEqualTo(saveVoucherId1);
        assertThat(voucher2).isEqualTo(saveVoucherId2);
    }

    @Test
    @DisplayName("바우처 목록 조회에 성공한다.")
    void viewAllVouchers() {
        // given
        Voucher voucher1 = Voucher.toVoucher(UUID.randomUUID(), 1000, VoucherType.FIXED);
        Voucher voucher2 = Voucher.toVoucher(UUID.randomUUID(), 10, VoucherType.PERCENT);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        List<Voucher> vouchers = voucherRepository.findAll();

        // then
        assertThat(vouchers).hasSize(2);
        assertThat(vouchers).contains(voucher1);
        assertThat(vouchers).contains(voucher2);
    }

    @Test
    @DisplayName("바우처 번호로 바우처를 조회에 성공한다.")
    void findByVoucherId() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher1 = Voucher.toVoucher(voucherId1, 1000, VoucherType.FIXED);

        // when
        Voucher saveVoucherId = voucherRepository.save(voucher1);
        Voucher findVoucherId = voucherRepository.findById(voucherId1).get();

        // then
        assertThat(findVoucherId).isEqualTo(saveVoucherId);
    }

    @Test
    @DisplayName("바우처 번호로 바우처를 조회에 실패한다.")
    void findByVoucherIdFail() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher1 = Voucher.toVoucher(voucherId1, 1000, VoucherType.FIXED);

        // when
        voucherRepository.save(voucher1);
        Optional<Voucher> findVoucher = voucherRepository.findById(UUID.randomUUID());

        // then
        assertThat(findVoucher).isEmpty();
    }

}