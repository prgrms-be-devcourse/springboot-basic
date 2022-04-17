package org.prgrms.springbootbasic.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;

@TestInstance(Lifecycle.PER_CLASS)
class FileVoucherRepositoryTest {

    FileVoucherRepository fileVoucherRepository = new FileVoucherRepository();

    @BeforeAll
    void setup() {
        fileVoucherRepository.removeAll();
    }

    @AfterEach
    void clean() {
        fileVoucherRepository.removeAll();
    }

    @DisplayName("save 테스트")
    @Test
    void save() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        Voucher percentAmountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        //when
        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentAmountVoucher);

        //then
        assertThat(fileVoucherRepository.findAll().size()).isEqualTo(2);
    }

    @DisplayName("find All 테스트")
    @Test
    void finaAll() {
        //given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        var percentAmountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentAmountVoucher);

        //when
        List<Voucher> vouchers = fileVoucherRepository.findAll();

        //then
        assertAll(
            () -> assertEquals(fixedAmountVoucher.getVoucherId(), vouchers.get(0).getVoucherId()),
            () -> assertEquals(fixedAmountVoucher.getAmount(),
                ((FixedAmountVoucher) vouchers.get(0)).getAmount()),
            () -> assertEquals(percentAmountVoucher.getVoucherId(), vouchers.get(1).getVoucherId()),
            () -> assertEquals(percentAmountVoucher.getPercent(),
                ((PercentDiscountVoucher) vouchers.get(1)).getPercent())
        );
    }
}