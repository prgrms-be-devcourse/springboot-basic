package org.prgrms.springbootbasic.controller;

import org.prgrms.springbootbasic.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebVoucherController {

    private final VoucherService voucherService;

    public WebVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        var voucherDTOs = voucherService.findAll();
        model.addAttribute("voucherDTOs", voucherDTOs);
        return "vouchers";
    }
}
