package org.prgrms.kdtspringdemo.domain.voucher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/")
    public String StartPage() {
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers")
    public String VouchersPage(Model model) {
        var vouchers = voucherService.getAllVoucherList();
        model.addAttribute("vouchers", vouchers);
        return "vouchers";
    }

}
