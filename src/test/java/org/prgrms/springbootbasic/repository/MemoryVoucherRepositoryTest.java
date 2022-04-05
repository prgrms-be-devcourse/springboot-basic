package org.prgrms.springbootbasic.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
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

}