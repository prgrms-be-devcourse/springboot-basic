package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.PercentDiscountVoucher;
import com.dev.bootbasic.voucher.domain.Voucher;
import com.dev.bootbasic.voucher.domain.VoucherFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryVoucherRepositoryTest {

    private final VoucherRepository voucherRepository = new InMemoryVoucherRepository();

    @DisplayName("바우처를 저장할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "fixed,5000",
            "percent,50"
    }
    )
    void saveVoucherTest(String type, int discountAmount) {
        VoucherFactory voucherFactory = new VoucherFactory();
        Voucher voucher = voucherFactory.create(randomUUID(), type, discountAmount);


        UUID foundVoucherId = voucherRepository.saveVoucher(voucher);

        Optional<Voucher> foundPercentVoucher = voucherRepository.findVoucher(foundVoucherId);
        assertThat(foundPercentVoucher.get()).isEqualTo(voucher);
    }

}
