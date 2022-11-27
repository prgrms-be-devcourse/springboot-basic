package org.programmers.program.voucher.controller.api;

import org.programmers.program.voucher.model.Voucher;
import org.programmers.program.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public List<Voucher> voucherLists(){
        var allVouchers = voucherService.getAllVouchers();
        return allVouchers;
    }
}
