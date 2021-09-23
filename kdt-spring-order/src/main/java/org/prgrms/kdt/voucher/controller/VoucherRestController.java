package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/list")
    public List<Voucher> getVoucherList(){
        return voucherService.getVoucherList();
    }


}
