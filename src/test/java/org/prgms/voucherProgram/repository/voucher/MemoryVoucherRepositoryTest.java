package org.prgms.voucherProgram.repository.voucher;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucherProgram.domain.voucher.FixedAmountVoucher;
import org.prgms.voucherProgram.domain.voucher.PercentDiscountVoucher;
import org.prgms.voucherProgram.domain.voucher.Voucher;

class MemoryVoucherRepositoryTest {

    VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @DisplayName("Voucher를 저장한다.")
    @Test
    void save_Voucher() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        // when
        Voucher saveVoucher = voucherRepository.save(voucher);
        // then
        assertThat(saveVoucher).isEqualTo(voucher);
        assertThat(voucherRepository.findAll()).hasSize(1)
            .contains(voucher);
    }

    @DisplayName("저장되어 있는 모든 Voucher를 List형으로 반환한다.")
    @Test
    void findAll_ReturnAllVoucher() {
        // given
        Voucher voucherOne = new FixedAmountVoucher(UUID.randomUUID(), 20);
        Voucher voucherTwo = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        // when
        voucherRepository.save(voucherOne);
        voucherRepository.save(voucherTwo);
        // then
        assertThat(voucherRepository.findAll()).hasSize(2)
            .contains(voucherOne, voucherTwo);
    }
}
