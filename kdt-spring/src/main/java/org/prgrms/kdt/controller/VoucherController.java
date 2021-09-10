package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/voucher")
@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String viewVouchers(Model model) {
        List<Voucher> vouchers = voucherService.vouchers();

        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @GetMapping("/new-form")
    public String viewCreate() {
        return "voucher/new-voucher";
    }

    @PostMapping("/new-form")
    public String createVoucher(@RequestParam("type") String type, @RequestParam("value") Long value) {

        voucherService.insert(VoucherType.valueOf(type), value);

        return "redirect:/voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String viewUpdate(@PathVariable("voucherId") UUID voucherId, Model model) {

        Voucher voucher = voucherService.findById(voucherId);

        model.addAttribute("voucher", voucher);
        return "/voucher/update-voucher";
    }

    @PutMapping("/{voucherId}")
    public String updateVoucher(@PathVariable("voucherId") UUID voucherId, @RequestParam("value") Long value) {
        voucherService.update(voucherId, value);
        return "redirect:/voucher/vouchers";
    }

    @DeleteMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);
        return "redirect:/voucher/vouchers";
    }
}
