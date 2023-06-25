package com.programmers.voucher.repository;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class MemoryVoucherRepositoryTest {
    private static final int PERCENT_DISCOUNT_AMOUNT = 10;
    private static final int FIXED_DISCOUNT_AMOUNT = 100;
    private static final int EXPECTED_COUNT = 2;

    private VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @DisplayName("바우처 저장 성공 테스트")
    @Test
    void save() {
        //give
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = FixedAmountVoucher.of(voucherId, FIXED_DISCOUNT_AMOUNT);

        //when
        voucherRepository.save(voucher);

        //then
        Voucher savedVoucher = voucherRepository.findByVoucherId(voucherId).get();
        Assertions.assertThat(voucher).isEqualTo(savedVoucher);
    }

    @DisplayName("바우처 목록 조회 성공 테스트")
    @Test
    void findAll() {
        //give
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher1 = FixedAmountVoucher.of(voucherId1, FIXED_DISCOUNT_AMOUNT);

        UUID voucherId2 = UUID.randomUUID();
        Voucher voucher2 = PercentDiscountVoucher.of(voucherId2, PERCENT_DISCOUNT_AMOUNT);

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        //when
        List<Voucher> voucherList = voucherRepository.findAll();

        //then
        Assertions.assertThat(voucherList.size()).isEqualTo(EXPECTED_COUNT);
    }
}
