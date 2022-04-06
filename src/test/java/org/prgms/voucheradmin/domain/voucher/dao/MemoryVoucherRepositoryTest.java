package org.prgms.voucheradmin.domain.voucher.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;

class MemoryVoucherRepositoryTest {
    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @Test
    void 저장_조회_테스트() {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Voucher voucher2 = new PercentageDiscountVoucher(UUID.randomUUID(), 10L);

        memoryVoucherRepository.save(voucher1);
        memoryVoucherRepository.save(voucher2);

        List<Voucher> vouchers = memoryVoucherRepository.getAll();

        assertThat(vouchers.size()).isEqualTo(2);
    }
}