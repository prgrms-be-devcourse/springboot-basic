package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("test", "hello world");
        return "admin";
    }

    @GetMapping("/voucher")
    public String voucher(Model model) {
        model.addAttribute("test", "hello world");
        return "voucher";
    }
}
