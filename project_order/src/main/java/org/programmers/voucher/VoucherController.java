package org.programmers.voucher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/vouchers/{voucherId}")
    public String viewVoucherDetails(@PathVariable("voucherId") UUID voucherId, Model model) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "views/voucher-details";
    }

    @GetMapping("/vouchers/new")
    public String viewNewVoucher() {
        return "views/voucher-new";
    }

    @PostMapping("/vouchers/new")
    public String addNewVoucher(VoucherRequest voucherRequest, Model model) {
        Voucher voucher = voucherService.createVoucher(voucherRequest.voucherType(), UUID.randomUUID(), voucherRequest.discountValue());
        model.addAttribute("voucher", voucher);
        return "redirect:/vouchers";
    }

    @GetMapping("vouchers/{voucherId}/delete")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }

}
