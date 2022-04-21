package org.prgrms.springbootbasic.controller;

import java.util.UUID;
import org.prgrms.springbootbasic.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/vouchers/{voucherId}")
    public String viewVoucherPage(@PathVariable("voucherId") UUID voucherId, Model model) {
        var voucherDTO = voucherService.findVoucher(voucherId);
        model.addAttribute("voucher", voucherDTO);
        return "voucher";
    }
}
