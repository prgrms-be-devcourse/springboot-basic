package org.prgrms.kdt.shop.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.dto.VoucherCreateServiceDto;
import org.prgrms.kdt.shop.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
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
        model.addAttribute("voucher", voucherService.findVoucherById(voucherId));
        return "basic/voucher";
    }

    @GetMapping("/add")
    public String addVoucherForm() {
        return "basic/addForm";
    }

    @PostMapping("/add")
    public String addVoucher(@RequestParam int voucherAmount, @RequestParam String voucherType,
        Model model, RedirectAttributes redirectAttributes) {
        Voucher voucher = voucherService.addVoucher(
            new VoucherCreateServiceDto(UUID.randomUUID(), voucherAmount, voucherType,
                LocalDateTime.now()));
        redirectAttributes.addAttribute("voucherId", voucher.getVoucherId());
        return "redirect:/basic/vouchers/{voucherId}";
    }

    @DeleteMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId,
        RedirectAttributes redirectAttributes) {
        voucherService.deleteVoucherById(voucherId);
        redirectAttributes.addAttribute("voucher", voucherId);
        return "redirect:/basic/vouchers";
    }

    @DeleteMapping
    public String deleteAllVoucher(RedirectAttributes redirectAttributes) {
        voucherService.deleteAllVoucher();
        List<Voucher> voucherList = voucherService.findAllVoucher();
        redirectAttributes.addAttribute("vouchers", voucherList);
        return "redirect:/basic/vouchers";
    }
}
