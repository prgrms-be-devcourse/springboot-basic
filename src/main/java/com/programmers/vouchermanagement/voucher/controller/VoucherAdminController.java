package com.programmers.vouchermanagement.voucher.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;

@Controller
@RequestMapping("/vouchers")
public class VoucherAdminController {
    private final VoucherService voucherService;

    public VoucherAdminController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String readAllVouchers(Model model) {
        List<VoucherResponse> vouchers = voucherService.readAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers/vouchers";
    }
}
