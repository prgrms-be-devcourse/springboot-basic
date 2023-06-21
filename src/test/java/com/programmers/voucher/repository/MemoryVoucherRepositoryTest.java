package com.programmers.voucher.repository;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class MemoryVoucherRepositoryTest {

    public static final int FIXED_DISCOUNT_AMOUNT = 100;
    public static final int PERCENT_DISCOUNT_AMOUNT = 10;
    VoucherRepository voucherRepository;

    @BeforeEach
    void 초기화() {
        voucherRepository = new MemoryVoucherRepository();
        voucherRepository.clear();
    }

    @Test
    void save() {
        //give
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, FIXED_DISCOUNT_AMOUNT);

        //when
        voucherRepository.save(voucher);

        //then
        Voucher savedVoucher = voucherRepository.findByVoucherId(voucherId).get();
        Assertions.assertThat(voucher).isEqualTo(savedVoucher);
    }

    @Test
    void findAll() {
        //give
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher1 = new FixedAmountVoucher(voucherId1, FIXED_DISCOUNT_AMOUNT);

        UUID voucherId2 = UUID.randomUUID();
        Voucher voucher2 = new PercentDiscountVoucher(voucherId2, PERCENT_DISCOUNT_AMOUNT);

        //when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        List<Voucher> voucherList = voucherRepository.findAll();

        //then
        Assertions.assertThat(voucherList.size()).isEqualTo(2);
    }
}