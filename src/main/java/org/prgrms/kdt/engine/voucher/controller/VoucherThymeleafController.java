package org.prgrms.kdt.engine.voucher.controller;

import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.prgrms.kdt.engine.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherThymeleafController {
    private final VoucherService voucherService;

    public VoucherThymeleafController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String vouchersPage(Model model) {
        Optional<Map<UUID, Voucher>> vouchers = voucherService.listVoucher();
        model.addAttribute("vouchers", vouchers.get());
        return "voucher-list";
    }

    @DeleteMapping("/vouchers/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }
}
