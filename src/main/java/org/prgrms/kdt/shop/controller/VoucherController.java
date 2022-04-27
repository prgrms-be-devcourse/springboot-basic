package org.prgrms.kdt.shop.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.shop.domain.FixedAmountVoucher;
import org.prgrms.kdt.shop.domain.PercentDiscountVoucher;
import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.enums.VoucherType;
import org.prgrms.kdt.shop.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/basic/vouchers")
public class VoucherController {
    private final VoucherService voucherService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    @GetMapping
    public String getVouchers(Model model) {
        List<Voucher> voucherList = voucherService.findAllVoucher();
        model.addAttribute("vouchers", voucherList);
        return "basic/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String getVoucherById(@PathVariable UUID voucherId, Model model) {
        Optional<Voucher> voucher = voucherService.findVoucherById(voucherId);
        model.addAttribute("voucher", voucher.get());
        return "basic/voucher";
    }

    @GetMapping("/add")
    public String addVoucherForm( ) {
        return "basic/addForm";
    }

    @PostMapping("/add")
    @Transactional
    public String addVoucher(@RequestParam int voucherAmount, @RequestParam String voucherType, Model model, RedirectAttributes redirectAttributes) {
        Voucher voucher;
        try {
            if (VoucherType.find(voucherType) == VoucherType.FIXED_AMOUNT) {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), voucherAmount);
            } else if (VoucherType.find(voucherType) == VoucherType.PERCENT_DISCOUNT) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), voucherAmount);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            logger.error("Tpye Error", e);
            List<Voucher> voucherList = voucherService.findAllVoucher();
            model.addAttribute("vouchers", voucherList);
            return "basic/vouchers";
        }
        voucherService.addVoucher(voucher);
        redirectAttributes.addAttribute("voucherId", voucher.getVoucherId());
        return "redirect:/basic/vouchers/{voucherId}";
    }

    @DeleteMapping("/{voucherId}")
    @Transactional
    public String deleteVoucher(@PathVariable UUID voucherId, RedirectAttributes redirectAttributes) {
        voucherService.deleteVoucherById(voucherId);
        List<Voucher> voucherList = voucherService.findAllVoucher();
        redirectAttributes.addAttribute("voucher", voucherId);
        return "redirect:/basic/vouchers";
    }

    @DeleteMapping
    @Transactional
    public String deleteAllVoucher(RedirectAttributes redirectAttributes) {
        voucherService.deleteAllVoucher();
        List<Voucher> voucherList = voucherService.findAllVoucher();
        redirectAttributes.addAttribute("vouchers", voucherList);
        return "redirect:/basic/vouchers";
    }
}
