package org.prgrms.kdt.voucher;

import javax.validation.Valid;
import org.prgrms.kdt.form.VoucherForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute(new VoucherForm());
        return "admin/voucher/form";
    }

    @PostMapping("/admin/voucher/form")
    public String submit(@Valid VoucherForm voucherForm, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "/admin/voucher/form";
        }

        voucherService.addVoucher(voucherForm);
        attributes.addFlashAttribute("message", "바우처가 정상적으로 등록되었습니다.");
        return "redirect:/admin";
    }
}
