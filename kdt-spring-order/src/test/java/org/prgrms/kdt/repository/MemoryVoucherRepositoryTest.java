package org.prgrms.kdt.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.Voucher;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRepositoryTest {

    VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @BeforeEach
    void beforeEach() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("저장된 바우처를 voucherId를 이용해 찾는 기능 검증")
    void voucherId_이용한_바우처찾기() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);

        // when
        voucherRepository.insert(voucher);
        Voucher findVoucher = voucherRepository.findById(voucher.getVoucherId()).get();

        // then
        assertThat(voucher.getVoucherId()).isEqualTo(findVoucher.getVoucherId());
    }

    @Test
    @DisplayName("바우처가 제대로 들어갔는지 repository 내부 바우처의 갯수로 검증")
    void insert() {
        // given
        int size = 0;
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        size += 1;

        // when
        voucherRepository.insert(voucher);
        int getVoucherCount = voucherRepository.getAllStoredVoucher().size();

        // then
        assertThat(size).isEqualTo(getVoucherCount);
    }

    @Test
    @DisplayName("저장된 바우처들이 제대로 나오는지 바우처 갯수를 이용한 검증")
    void getAllStoredVoucher() {
        // given
        int size = 3;
        for (int i = 1; i <= size; i++) {
            voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 1000L*i));
        }

        // when
        int getVoucherCount = voucherRepository.getAllStoredVoucher().size();

        // then
        assertThat(size).isEqualTo(getVoucherCount);
    }

}