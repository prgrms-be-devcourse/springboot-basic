package org.prgrms.kdt.domain.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @Test
    @DisplayName("id를 통해 저장된 바우처를 조회할 수 있다.")
    public void findById(){
        //given
        UUID fixedVoucherId = UUID.randomUUID();
        UUID percentVoucherId = UUID.randomUUID();
        Voucher fixedVoucher = new FixedAmountVoucher(fixedVoucherId, 10000);
        Voucher percentVoucher = new PercentDiscountVoucher(percentVoucherId, 50);
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(percentVoucher);
        //when
        Optional<Voucher> findFixedVoucher = voucherRepository.findById(fixedVoucherId);
        Optional<Voucher> findPercentVoucher = voucherRepository.findById(percentVoucherId);
        //then
        assertThat(findFixedVoucher.orElse(null)).isEqualTo(fixedVoucher);
        assertThat(findPercentVoucher.orElse(null)).isEqualTo(percentVoucher);
    }

    @Test
    @DisplayName("바우처가 정상적으로 저장된다.")
    public void saveVoucher(){
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 10000);
        //when
        UUID savedVoucherId = voucherRepository.save(voucher);
        //then
        assertThat(savedVoucherId).isEqualTo(voucherId);
    }

    @Test
    @DisplayName("저장된 바우처를 조회할 수 있다.")
    public void findAllVouchers(){
        //given
        UUID fixedVoucherId = UUID.randomUUID();
        UUID percentVoucherId = UUID.randomUUID();
        Voucher fixedVoucher = new FixedAmountVoucher(fixedVoucherId, 10000);
        Voucher percentVoucher = new PercentDiscountVoucher(percentVoucherId, 30);
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(percentVoucher);
        //when
        List<Voucher> vouchers = voucherRepository.findAll();
        //then
        assertThat(vouchers).contains(fixedVoucher, percentVoucher);
    }
}