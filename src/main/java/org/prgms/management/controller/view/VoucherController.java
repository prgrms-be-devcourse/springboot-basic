package org.prgms.management.controller.view;

import org.prgms.management.model.voucher.VoucherDto;
import org.prgms.management.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping({"/vouchers", "/"})
    public String vouchersPage(Model model) {
        var vouchers = voucherService.getAll();
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher-list";
    }

    @GetMapping("/voucher/{id}")
    public String voucherPage(Model model, @PathVariable("id") UUID id) {
        var voucher = voucherService.getById(id);
        if (voucher.isEmpty()) {
            return "error/404";
        }
        model.addAttribute("voucher", voucher.get());
        return "voucher/voucher-detail";
    }

    @GetMapping("/voucher/add")
    public String addVoucherPage() {
        return "voucher/voucher-add";
    }

    @PostMapping("/voucher/add")
    public String addVoucherPage(VoucherDto voucherDto) {
        voucherService.create(voucherDto.voucherName(),
                voucherDto.voucherType(),
                voucherDto.discountNum());
        return "redirect:/vouchers";
    }

    @GetMapping("/voucher/delete/{id}")
    public String deleteVoucherPage(@PathVariable("id") UUID id) {
        voucherService.delete(id);
        return "redirect:/vouchers";
    }
}
