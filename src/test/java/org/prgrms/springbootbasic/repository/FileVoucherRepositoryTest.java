package org.prgrms.springbootbasic.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;

class FileVoucherRepositoryTest {

    FileVoucherRepository fileVoucherRepository = new FileVoucherRepository();

    @AfterEach
    void init() {
        fileVoucherRepository.removeAll();
    }

    @DisplayName("save 테스트")
    @Test
    void save() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Voucher percentAmountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        //when
        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentAmountVoucher);

        //then
        assertThat(fileVoucherRepository.getVoucherTotalNumber()).isEqualTo(2);
    }

    @DisplayName("find All 테스트")
    @Test
    void finaAll() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Voucher percentAmountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentAmountVoucher);

        //when
        List<Voucher> vouchers = fileVoucherRepository.findAll();

        //then
        assertAll(
            () -> assertEquals(fixedAmountVoucher.getVoucherId(), vouchers.get(0).getVoucherId()),
            () -> assertEquals(percentAmountVoucher.getVoucherId(), vouchers.get(1).getVoucherId())
        );
    }
}