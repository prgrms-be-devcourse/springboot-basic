package com.dev.bootbasic.voucher.repository;

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
    void saveVoucherTest() {
        PercentDiscountVoucher percentVoucher = createPercentVoucher(50);

        UUID percentVoucherId = voucherRepository.saveVoucher(percentVoucher);

        Optional<Voucher> foundPercentVoucher = voucherRepository.findVoucher(percentVoucherId);
        assertThat(foundPercentVoucher.get()).isEqualTo(percentVoucher);
    }

    private PercentDiscountVoucher createPercentVoucher(int discountAmount) {
        return PercentDiscountVoucher.of(UUID.randomUUID(), discountAmount);
    }

}
