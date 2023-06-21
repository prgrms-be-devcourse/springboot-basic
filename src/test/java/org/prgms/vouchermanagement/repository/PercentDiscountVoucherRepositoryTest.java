package org.prgms.vouchermanagement.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.vouchermanagement.voucher.FixedAmountVoucher;
import org.prgms.vouchermanagement.voucher.PercentDiscountVoucher;
import org.prgms.vouchermanagement.voucher.Voucher;

import java.util.Optional;
import java.util.UUID;

class PercentDiscountVoucherRepositoryTest {

    PercentDiscountVoucherRepository percentDiscountVoucherRepository;

    @BeforeEach
    void beforeEach() {
        percentDiscountVoucherRepository = new PercentDiscountVoucherRepository();
    }

    @Test
    @DisplayName("PercentDiscountVoucher 저장 Test")
    void saveVoucher() {
        //given
        PercentDiscountVoucher testInputVoucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 30);
        PercentDiscountVoucher testInputVoucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 60);

        //when
        percentDiscountVoucherRepository.saveVoucher(testInputVoucher1.getVoucherId(), testInputVoucher1);
        percentDiscountVoucherRepository.saveVoucher(testInputVoucher2.getVoucherId(), testInputVoucher2);

        Optional<Voucher> testOutputVoucher1 = percentDiscountVoucherRepository.findById(testInputVoucher1.getVoucherId());
        Optional<Voucher> testOutputVoucher2 = percentDiscountVoucherRepository.findById(testInputVoucher2.getVoucherId());

        //then
        Assertions.assertThat(testOutputVoucher1.get()).isEqualTo(testInputVoucher1);
        Assertions.assertThat(testOutputVoucher2.get()).isEqualTo(testInputVoucher2);
    }
}