package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoucherApiController {

    private final JdbcVoucherService voucherService;

    public VoucherApiController(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/vouchers")
    public List<Voucher> getVouchers() {
        return voucherService.getAllVouchers();
    }

}
