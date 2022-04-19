package org.prgrms.springbootbasic.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

class FileVoucherRepositoryTest {

    static FileVoucherRepository fileVoucherRepository;

    @BeforeAll
    static void beforeAll() {
        fileVoucherRepository = new FileVoucherRepository();
        fileVoucherRepository.removeAll();
    }

    @AfterEach
    void clean() {
        fileVoucherRepository.removeAll();
    }

    @DisplayName("FixedAmountVoucher 저장")
    @Test
    void saveFixedAmountVoucher() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 10);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 20);

        //when
        fileVoucherRepository.save(voucher1);
        fileVoucherRepository.save(voucher2);

        //then
        assertThat(fileVoucherRepository.findAll())
            .extracting(Voucher::getVoucherId)
            .containsExactlyInAnyOrder(voucher1.getVoucherId(), voucher2.getVoucherId());
    }

    @DisplayName("PercentAmountVoucher 저장")
    @Test
    void savePercentAmountVoucher() {
        //given
        Voucher voucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        //when
        fileVoucherRepository.save(voucher1);
        fileVoucherRepository.save(voucher2);

        //then
        assertThat(fileVoucherRepository.findAll())
            .extracting(Voucher::getVoucherId)
            .containsExactlyInAnyOrder(voucher1.getVoucherId(), voucher2.getVoucherId());
    }

    @DisplayName("FixedAmountVoucher, PercentAmountVoucher 저장")
    @Test
    void save() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        Voucher percentAmountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        //when
        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentAmountVoucher);

        //then
        assertThat(fileVoucherRepository.findAll())
            .extracting(Voucher::getVoucherId)
            .containsExactlyInAnyOrder(fixedAmountVoucher.getVoucherId(),
                percentAmountVoucher.getVoucherId());
    }

    @DisplayName("find All 테스트")
    @Test
    void finaAll() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
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
