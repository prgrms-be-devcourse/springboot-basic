package org.programmers.voucher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String viewAllVouchers(Model model) {
        List<Voucher> allVouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", allVouchers);
        return "views/vouchers";
    }

}
