package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.FixedAmountVoucher;
import com.dev.bootbasic.voucher.domain.PercentDiscountVoucher;
import com.dev.bootbasic.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryVoucherRepositoryTest {

    private final VoucherRepository voucherRepository = new InMemoryVoucherRepository();

    @DisplayName("바우처를 저장할 수 있다.")
    @Test
    void saveVoucherTest(){
        FixedAmountVoucher fixedVoucher = createFixedVoucher(5000);
        PercentDiscountVoucher percentVoucher = createPercentVoucher(50);

        UUID fixedVoucherId = voucherRepository.saveVoucher(fixedVoucher);
        UUID percentVoucherId = voucherRepository.saveVoucher(percentVoucher);

        Optional<Voucher> foundFixedVoucher = voucherRepository.findVoucher(fixedVoucherId);
        Optional<Voucher> foundPercentVoucher = voucherRepository.findVoucher(percentVoucherId);

        assertThat(foundFixedVoucher.get()).isEqualTo(fixedVoucher);
        assertThat(foundPercentVoucher.get()).isEqualTo(percentVoucher);
     }

    private FixedAmountVoucher createFixedVoucher(int discountAmount) {
        return FixedAmountVoucher.of(UUID.randomUUID(), discountAmount);
    }

    private PercentDiscountVoucher createPercentVoucher(int discountAmount) {
        return PercentDiscountVoucher.of(UUID.randomUUID(), discountAmount);
    }

}
