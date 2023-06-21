package org.prgms.vouchermanagement.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.vouchermanagement.voucher.FixedAmountVoucher;
import org.prgms.vouchermanagement.voucher.Voucher;

import java.util.Optional;
import java.util.UUID;

class FixedAmountVoucherRepositoryTest {

    FixedAmountVoucherRepository fixedAmountVoucherRepository;

    @BeforeEach
    void beforeEach() {
        fixedAmountVoucherRepository = new FixedAmountVoucherRepository();
    }

    @Test
    @DisplayName("FixedAmountVoucher 저장 Test")
    void saveVoucher() {
        //given
        FixedAmountVoucher testInputVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        FixedAmountVoucher testInputVoucher2 = new FixedAmountVoucher(UUID.randomUUID(), 5000L);

        //when
        fixedAmountVoucherRepository.saveVoucher(testInputVoucher1.getVoucherId(), testInputVoucher1);
        fixedAmountVoucherRepository.saveVoucher(testInputVoucher2.getVoucherId(), testInputVoucher2);

        Optional<Voucher> testOutputVoucher1 = fixedAmountVoucherRepository.findById(testInputVoucher1.getVoucherId());
        Optional<Voucher> testOutputVoucher2 = fixedAmountVoucherRepository.findById(testInputVoucher2.getVoucherId());

        //then
        Assertions.assertThat(testOutputVoucher1.get()).isEqualTo(testInputVoucher1);
        Assertions.assertThat(testOutputVoucher2.get()).isEqualTo(testInputVoucher2);
    }
}