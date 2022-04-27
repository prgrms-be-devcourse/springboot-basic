package org.prgrms.vouchermanager.domain.voucher.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.domain.Voucher;
import org.prgrms.vouchermanager.domain.voucher.domain.VoucherRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemoryVoucherRepositoryTest {

    @Test
    @DisplayName("Voucher를 insert하고 똑같은 객체를 반환받는다.")
    void testWithInsert() {
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(),10);
        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);

        voucherRepository.insert(fixedVoucher);
        voucherRepository.insert(percentVoucher);

        assertThat(voucherRepository.findById(fixedVoucher.getId()).get()).isEqualTo(fixedVoucher);
        assertThat(voucherRepository.findById(percentVoucher.getId()).get()).isEqualTo(percentVoucher);
    }

    @Test
    @DisplayName("voucherId로 Voucher를 찾을 수 있다.")
    void testWithFindById() {
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(),10);
        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);
        voucherRepository.insert(fixedVoucher);
        voucherRepository.insert(percentVoucher);

        Voucher findFixedVoucher = voucherRepository.findById(fixedVoucher.getId()).get();
        Voucher findPercentVoucher = voucherRepository.findById(percentVoucher.getId()).get();

        assertThat(findFixedVoucher).isEqualTo(fixedVoucher);
        assertThat(findPercentVoucher).isEqualTo(percentVoucher);
    }

    @Test
    @DisplayName("이미 존재하는 voucher를 insert 할 수 없다.")
    void testWithDuplicatedInsert() {
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(),10);

        voucherRepository.insert(fixedVoucher);

        assertThatThrownBy(() -> voucherRepository.insert(fixedVoucher)).isInstanceOf(IllegalStateException.class);
    }

}
