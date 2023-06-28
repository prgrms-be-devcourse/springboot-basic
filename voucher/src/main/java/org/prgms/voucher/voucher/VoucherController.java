package org.prgms.voucher.voucher;

import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void createVoucher(VoucherCreateDto voucherCreateDto) {
        voucherService.createVoucher(voucherCreateDto);
    }

    public void listVoucher() {
        voucherService.listVoucher();
    }
}
