package org.prgrms.springbootbasic.repository;

import java.util.UUID;
import org.assertj.core.api.Assertions;
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
        Assertions.assertThat(fileVoucherRepository.getVoucherTotalNumber()).isEqualTo(2);
    }
}