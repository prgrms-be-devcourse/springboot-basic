package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.voucher.domain.VoucherType.FIXED;
import static org.prgrms.kdt.voucher.domain.VoucherType.PERCENT;

@Controller
public class VoucherThymeleafController {

    private final VoucherService voucherService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherThymeleafController.class);

    public VoucherThymeleafController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String showVouchers(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher-list";
    }

    @GetMapping("/vouchers/create")
    public String showCreateVoucherForm() {
        return "voucher/voucher-create";
    }

    @PostMapping("/vouchers/create")
    public String createVoucher(@RequestParam("type") String voucherType,
                                @RequestParam(value = "amount", required = false) Integer amount,
                                @RequestParam(value = "percent", required = false) Integer percent
    ) {
        if (voucherType.equals(FIXED.getType())) {
            voucherService.createFixedAmountVoucher(amount);
        } else if (voucherType.equals(PERCENT.getType())) {
            voucherService.createPercentDiscountVoucher(percent);
        } else {
            logger.error("Unknown voucher type: " + voucherType);
        }

        return "redirect:/vouchers";
    }

    @PostMapping("/vouchers/remove/{id}")
    public String removeVoucher(@PathVariable("id") String voucherId) {
        UUID id = UUID.fromString(voucherId);
        voucherService.removeVoucherById(id);
        return "redirect:/vouchers";
    }
}
