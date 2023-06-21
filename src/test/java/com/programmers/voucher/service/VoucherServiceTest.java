package com.programmers.voucher.service;

import com.programmers.voucher.exception.VoucherErrorCode;
import com.programmers.voucher.exception.VoucherException;
import com.programmers.voucher.repository.MemoryVoucherRepository;
import com.programmers.voucher.repository.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoucherServiceTest {

    public static final int FIXED_DISCOUNT_AMOUNT = 100;
    public static final int PERCENT_DISCOUNT_AMOUNT = 10;
    VoucherRepository voucherRepository;
    VoucherService voucherService;

    @BeforeEach
    void 초기화() {
        voucherRepository = new MemoryVoucherRepository();
        voucherRepository.clear();
        voucherService = new VoucherService(voucherRepository);
    }

    @Test
    void createFixedAmountVoucher() {
        voucherService.createVoucher("fixed", FIXED_DISCOUNT_AMOUNT);
    }

    @Test
    void createPercentDiscountVoucher() {
        voucherService.createVoucher("percent", PERCENT_DISCOUNT_AMOUNT);
    }

    @Test
    void 잘못된_바우처_타입_입력시_예외_발생() {
        Assertions.assertThatThrownBy(() -> voucherService.createVoucher("wrongType", FIXED_DISCOUNT_AMOUNT))
                .isInstanceOf(VoucherException.class)
                .hasMessage(VoucherErrorCode.NOT_SUPPORTED_TYPE.getErrorMessage());
    }

    @Test
    void findVoucherList() {
        voucherService.createVoucher("fixed", FIXED_DISCOUNT_AMOUNT);
        voucherService.createVoucher("percent", PERCENT_DISCOUNT_AMOUNT);
        Assertions.assertThat(voucherService.findVoucherList().size()).isEqualTo(2);
    }
}