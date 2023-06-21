package com.programmers.voucher.service;

import com.programmers.voucher.repository.MemoryVoucherRepository;
import com.programmers.voucher.repository.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoucherServiceTest {

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
        voucherService.createVoucher("fixed", 100);
    }

    @Test
    void createPercentDiscountVoucher() {
        voucherService.createVoucher("percent", 100);
    }

    @Test
    void 잘못된_바우처_타입_입력시_예외_발생() {
        Assertions.assertThatThrownBy(() -> voucherService.createVoucher("wrongType", 100))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findVoucherList() {
        voucherService.createVoucher("fixed", 100);
        voucherService.createVoucher("percent", 100);
        Assertions.assertThat(voucherService.findVoucherList().size()).isEqualTo(2);
    }
}