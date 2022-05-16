package org.prgrms.springbootbasic.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @AfterEach
    void rollback() {
        memoryVoucherRepository.removeAll();
    }

    @DisplayName("FixedAmountVoucher 저장 테스트")
    @Test
    void saveFixedAmountVoucher() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 10);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 10);

        //when
        memoryVoucherRepository.insert(voucher1);
        memoryVoucherRepository.insert(voucher2);

        //then
        assertThat(memoryVoucherRepository.findAll())
            .containsExactlyInAnyOrder(voucher1, voucher2);
    }

    @DisplayName("PercentAmountVoucher 저장 테스트")
    @Test
    void savePercentAmountVoucher() {
        //given
        Voucher voucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        //when
        memoryVoucherRepository.insert(voucher1);
        memoryVoucherRepository.insert(voucher2);

        //then
        assertThat(memoryVoucherRepository.findAll())
            .containsExactlyInAnyOrder(voucher1, voucher2);
    }

    @DisplayName("FixedAmountVoucher, PercentAmountVoucher 저장 테스트")
    @Test
    void store() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        //when
        memoryVoucherRepository.insert(fixedAmountVoucher);
        memoryVoucherRepository.insert(percentDiscountVoucher);

        //then
        assertThat(memoryVoucherRepository.findAll())
            .containsExactlyInAnyOrder(fixedAmountVoucher, percentDiscountVoucher);
    }

    @DisplayName("전체 조회 테스트")
    @Test
    void findAll() {
        //given
        var voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 10);
        memoryVoucherRepository.insert(voucher1);
        var voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        memoryVoucherRepository.insert(voucher2);

        //when
        List<Voucher> vouchers = memoryVoucherRepository.findAll();

        //then
        assertThat(vouchers)
            .containsExactlyInAnyOrder(voucher1, voucher2);
    }
}
