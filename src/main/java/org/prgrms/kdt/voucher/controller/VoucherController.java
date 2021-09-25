package org.prgrms.kdt.voucher.controller;

import java.util.UUID;
import org.prgrms.kdt.customer.controller.CustomerDto;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/")
    public String admin() {
        return "admin";
    }

    @GetMapping("/vouchers")
    public String voucherList(Model model) {
        var vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String voucherDetail(@PathVariable("voucherId") UUID voucherId, Model model) {
        var voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher-detail";
    }

    @PostMapping("/vouchers/{voucherId}")
    public String voucherUpdate(
        @PathVariable("voucherId") UUID voucherId,
        @ModelAttribute VoucherDto voucherDto) {
        voucherService.updateVoucher(voucherId, voucherDto);
        return "redirect:/vouchers";
    }


    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage() {
        return "voucher-new";
    }

    @PostMapping("/vouchers/new")
    public String addNewVoucher(@ModelAttribute VoucherDto voucherDto) {
        voucherService.createVoucher(voucherDto);
        return "redirect:/vouchers";
    }

    @PostMapping("/vouchers/{voucherId}/delete")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);
        return "redirect:/vouchers";
    }
}
