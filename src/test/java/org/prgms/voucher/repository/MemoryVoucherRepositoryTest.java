package org.prgms.voucher.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucher.entity.FixedAmountVoucher;
import org.prgms.voucher.entity.PercentDiscountVoucher;
import org.prgms.voucher.entity.Voucher;
import org.prgms.voucher.exception.WrongDiscountAmountException;
import org.prgms.voucher.exception.WrongDiscountPercentException;

class MemoryVoucherRepositoryTest {

    VoucherRepository repository = new MemoryVoucherRepository();

    @DisplayName("Voucher를 저장한다.")
    @Test
    void save_Voucher() throws WrongDiscountAmountException {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        // when
        Voucher insertVoucher = repository.save(voucher);
        // then
        assertThat(voucher).isEqualTo(insertVoucher);
        assertThat(repository.findAll()).hasSize(1)
            .contains(voucher);
    }

    @DisplayName("저장되어 있는 모든 Voucher를 List형으로 반환한다.")
    @Test
    void findAll_ReturnAllVoucher() throws WrongDiscountAmountException, WrongDiscountPercentException {
        // given
        Voucher voucherOne = new FixedAmountVoucher(UUID.randomUUID(), 20);
        Voucher voucherTwo = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        // when
        repository.save(voucherOne);
        repository.save(voucherTwo);
        // then
        assertThat(repository.findAll()).hasSize(2)
            .contains(voucherOne, voucherTwo);
    }
}
