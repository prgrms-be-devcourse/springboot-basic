package com.programmers.voucher.repository;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.request.VoucherCreationRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class MemoryVoucherRepositoryTest {
    public static final int FIXED_DISCOUNT_AMOUNT = 100;
    public static final int PERCENT_DISCOUNT_AMOUNT = 10;
    public static final String FIXED_AMOUNT_VOUCHER_TYPE = "fixed";
    public static final String PERCENT_DISCOUNT_VOUCHER_TYPE = "percent";

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
        VoucherCreationRequest voucherCreationRequest = new VoucherCreationRequest(FIXED_AMOUNT_VOUCHER_TYPE, FIXED_DISCOUNT_AMOUNT);
        Voucher voucher = new FixedAmountVoucher(voucherId, voucherCreationRequest);

        //when
        voucherRepository.save(voucher);

        //then
        Voucher savedVoucher = voucherRepository.findByVoucherId(voucherId).get();
        Assertions.assertThat(voucher).isEqualTo(savedVoucher);
    }

    @Test
    void findAll() {
        //give
        VoucherCreationRequest voucherCreationRequest1 = new VoucherCreationRequest(FIXED_AMOUNT_VOUCHER_TYPE, FIXED_DISCOUNT_AMOUNT);
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher1 = new FixedAmountVoucher(voucherId1, voucherCreationRequest1);

        VoucherCreationRequest voucherCreationRequest2 = new VoucherCreationRequest(PERCENT_DISCOUNT_VOUCHER_TYPE, PERCENT_DISCOUNT_AMOUNT);
        UUID voucherId2 = UUID.randomUUID();
        Voucher voucher2 = new PercentDiscountVoucher(voucherId2, voucherCreationRequest2);

        //when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        List<Voucher> voucherList = voucherRepository.findAll();

        //then
        Assertions.assertThat(voucherList.size()).isEqualTo(2);
    }
}