package com.demo.voucher.controller;

import com.demo.voucher.domain.Voucher;
import com.demo.voucher.domain.VoucherType;
import com.demo.voucher.repository.MemoryVoucherRepository;
import com.demo.voucher.repository.VoucherRepository;
import com.demo.voucher.service.VoucherService;
import com.demo.voucher.service.VoucherServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class VoucherControllerTest {

    VoucherRepository voucherRepository = new MemoryVoucherRepository();
    VoucherService voucherService = new VoucherServiceImpl(voucherRepository);
    VoucherController voucherController = new VoucherController(voucherService);

    @BeforeEach
    void beforeEach() {
        voucherRepository.clear();

        // given
        Voucher fixedVoucher = voucherService.createVoucher(VoucherType.FIXED_AMOUNT, "2000");
        Voucher percentVoucher = voucherController.createVoucher(VoucherType.PERCENT_DISCOUNT, "20");

        voucherRepository.insert(fixedVoucher);
        voucherRepository.insert(percentVoucher);
    }

    @Test
    void createVoucher() {
        // given
        Voucher fixedVoucher = voucherService.createVoucher(VoucherType.FIXED_AMOUNT, "4000");

        // when
        List<Voucher> vouchers = voucherController.getAllVouchers();

        // then
        Assertions.assertThat(vouchers)
                .hasSize(3)
                .contains(fixedVoucher);
    }

    @Test
    void getAllVouchers() {
        Assertions.assertThat(voucherController.getAllVouchers())
                .hasSize(2);
    }
}