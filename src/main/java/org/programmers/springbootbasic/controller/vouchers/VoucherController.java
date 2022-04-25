package org.programmers.springbootbasic.controller.vouchers;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("voucher")
    public String createForm(Model model) {
        var voucher = new VoucherCreateForm();
        model.addAttribute("voucher", voucher);
        model.addAttribute("voucherTypes", VoucherType.values());
        return "vouchers/createVoucher";
    }

    @PostMapping("voucher")
    public String createVoucher(@ModelAttribute("voucher") VoucherCreateForm voucherForm, RedirectAttributes redirectAttributes) {
        var createdVoucher = voucherService.createVoucher(voucherForm.getAmount(), voucherForm.getType());

        redirectAttributes.addAttribute("voucherId", createdVoucher.getId());
        return "redirect:/vouchers/{voucherId}";
    }

    @GetMapping("vouchers/{voucherId}")
    public String singleVoucherData(@PathVariable("voucherId")UUID voucherId, Model model) {
        VoucherDto voucher = VoucherDto.from(voucherService.getVoucher(voucherId));
        model.addAttribute("voucher", voucher);
        model.addAttribute("voucherTypes", VoucherType.values());

        return "vouchers/editVoucher";
    }

    @GetMapping("vouchers")
    public String voucherList(Model model) {
        List<VoucherDto> vouchers = new ArrayList<>();
        voucherService.getAllVouchers().forEach(voucher -> vouchers.add(VoucherDto.from(voucher)));
        model.addAttribute("vouchers", vouchers);

        return "vouchers/voucherList";
    }
}