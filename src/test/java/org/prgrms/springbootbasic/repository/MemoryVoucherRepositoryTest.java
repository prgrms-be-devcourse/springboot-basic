package org.prgrms.springbootbasic.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @AfterEach
    void rollback() {
        memoryVoucherRepository.removeAll();
    }

    @DisplayName("store 테스트")
    @Test
    void store() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);

        //when
        memoryVoucherRepository.save(voucher);

        //then
        assertThat(memoryVoucherRepository.getVoucherTotalNumber()).isEqualTo(1);
    }

    @DisplayName("전체 조회 테스트")
    @Test
    void findAll() {
        //given
        var voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        memoryVoucherRepository.save(voucher1);
        var voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        memoryVoucherRepository.save(voucher2);

        //when
        Optional<List<Voucher>> vouchers = memoryVoucherRepository.findAll();

        //then
        assertThat(vouchers.orElse(null))
            .containsExactlyInAnyOrder(voucher1, voucher2);
    }
}