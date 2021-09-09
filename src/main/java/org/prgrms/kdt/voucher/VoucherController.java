package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by yhh1056
 * Date: 2021/09/08 Time: 2:55 오후
 */

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/main";
    }

    @GetMapping("/admin/voucher/vouchers")
    public String vouchers(Model model) {
        model.addAttribute("vouchers", voucherService.getAllVouchers());
        return "admin/voucher/vouchers";
    }

    @GetMapping("/admin/voucher/form")
    public String form(Model model) {
        model.addAttribute(new VoucherDto());
        return "admin/voucher/form";
    }
}
