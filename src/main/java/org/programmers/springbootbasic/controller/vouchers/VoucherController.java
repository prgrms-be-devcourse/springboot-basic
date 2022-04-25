package org.programmers.springbootbasic.controller.vouchers;

import lombok.Data;
import org.programmers.springbootbasic.voucher.domain.VoucherType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoucherController {

    @GetMapping("voucher")
    public String voucherCreateForm(Model model) {
        var voucher = new VoucherForm();
        model.addAttribute("voucher", voucher);
        model.addAttribute("voucherTypes", VoucherType.values());
        return "voucher/createVoucher";
    }

    @Data
    static class VoucherForm {
        private VoucherType type;
        private Integer amount;
    }
}