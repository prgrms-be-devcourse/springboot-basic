package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.FixedAmountVoucherService;
import org.prgrms.kdt.voucher.service.PercentDiscountVoucherService;
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

@Controller
public class VoucherThymeleafController {

    private final VoucherService voucherService;
    private final FixedAmountVoucherService fixedAmountVoucherService;
    private final PercentDiscountVoucherService percentDiscountVoucherService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherThymeleafController.class);

    public VoucherThymeleafController(VoucherService voucherService, FixedAmountVoucherService fixedAmountVoucherService, PercentDiscountVoucherService percentDiscountVoucherService) {
        this.voucherService = voucherService;
        this.fixedAmountVoucherService = fixedAmountVoucherService;
        this.percentDiscountVoucherService = percentDiscountVoucherService;
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
        if (voucherType.equals("fixed")) {
            if (amount != null) {
                fixedAmountVoucherService.createFixedAmountVoucher(amount);
            } else {
                logger.error("Amount parameter is missing for fixed voucher creation.");
            }
        } else if (voucherType.equals("percent")) {
            if (percent != null) {
                percentDiscountVoucherService.createPercentDiscountVoucher(percent);
            } else {
                logger.error("Percent parameter is missing for percent voucher creation.");
            }
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
