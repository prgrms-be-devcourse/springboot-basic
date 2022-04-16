package org.prgms.voucheradmin.domain.voucher.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;

class MemoryVoucherRepositoryTest {
    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @Test
    @DisplayName("메모리 저장 조회 테스트")
    void testSaveAndGetAll() {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Voucher voucher2 = new PercentageDiscountVoucher(UUID.randomUUID(), 10L);

        memoryVoucherRepository.create(voucher1);
        memoryVoucherRepository.create(voucher2);

        List<Voucher> vouchers = memoryVoucherRepository.findAll();

        assertThat(vouchers.size()).isEqualTo(2);
    }
}