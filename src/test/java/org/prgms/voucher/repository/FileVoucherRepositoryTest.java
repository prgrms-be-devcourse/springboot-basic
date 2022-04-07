package org.prgms.voucher.repository;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucher.entity.FixedAmountVoucher;
import org.prgms.voucher.entity.PercentDiscountVoucher;
import org.prgms.voucher.entity.Voucher;
import org.prgms.voucher.exception.WrongDiscountAmountException;
import org.prgms.voucher.exception.WrongDiscountPercentException;

class FileVoucherRepositoryTest {

    VoucherRepository voucherRepository = new FileVoucherRepository();

    @AfterEach
    void tearDown() {
        File file = new File(FileVoucherRepository.FILE_NAME);
        file.delete();
    }

    @DisplayName("Voucher를 파일에 저장한다.")
    @Test
    void save_Voucher_File() throws WrongDiscountPercentException, WrongDiscountAmountException {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        // when
        Voucher saveVoucher = voucherRepository.save(voucher);
        // then
        assertThat(saveVoucher).isEqualTo(voucher);
        assertThat(voucherRepository.findAll()).hasSize(1)
            .extracting("voucherId").contains(voucher.getVoucherId());

    }

    @DisplayName("파일에 저장되어 있는 모든 Voucher를 List형으로 반환한다.")
    @Test
    void findAll_ReturnAllVoucher() throws WrongDiscountAmountException, WrongDiscountPercentException {
        // given
        Voucher voucherOne = new FixedAmountVoucher(UUID.randomUUID(), 20L);
        Voucher voucherTwo = new PercentDiscountVoucher(UUID.randomUUID(), 20L);
        // when
        voucherRepository.save(voucherOne);
        voucherRepository.save(voucherTwo);
        // then
        assertThat(voucherRepository.findAll()).hasSize(2)
            .extracting("voucherId").contains(voucherOne.getVoucherId(), voucherTwo.getVoucherId());
    }
}
