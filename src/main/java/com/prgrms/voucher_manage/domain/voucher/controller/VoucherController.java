package com.prgrms.voucher_manage.domain.voucher.controller;

import com.prgrms.voucher_manage.console.VoucherType;
import com.prgrms.voucher_manage.domain.voucher.dto.CreateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    public void createVoucher(VoucherType voucherType, Long discountAmount) {
        voucherService.createVoucher(new CreateVoucherDto(voucherType, discountAmount));
    }

    public void showVoucherList() {
        voucherService.showVoucherList();
    }
}
