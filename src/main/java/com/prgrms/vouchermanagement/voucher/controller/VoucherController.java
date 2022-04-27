package com.prgrms.vouchermanagement.voucher.controller;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.customer.CustomerDto;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import com.prgrms.vouchermanagement.wallet.VoucherWalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherWalletService walletService;
    private final VoucherService voucherService;

    public VoucherController(VoucherWalletService walletService, VoucherService voucherService) {
        this.walletService = walletService;
        this.voucherService = voucherService;
    }

    @GetMapping
    public String vouchers(Model model) {
        List<Voucher> vouchers = voucherService.findAllVouchers();
        model.addAttribute("vouchers", VoucherDto.fromList(vouchers));
        return "voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String findVoucher(@PathVariable UUID voucherId, Model model) {
        Optional<Voucher> findVoucher = voucherService.findVoucherById(voucherId);
        List<Customer> customers = walletService.findCustomerByVoucher(voucherId);

        if (findVoucher.isEmpty()) {
            return "error/404";
        }

        VoucherDto voucherDto = VoucherDto.from(findVoucher.get());
        model.addAttribute("voucherTypes", VoucherType.values());
        model.addAttribute("voucher", voucherDto);
        model.addAttribute("customers", CustomerDto.fromList(customers));
        return "voucher/voucher";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("voucher", VoucherDto.getEmptyVoucherDto());
        model.addAttribute("voucherTypes", VoucherType.values());
        return "voucher/addForm";
    }

    @PostMapping("/add")
    public String addVoucher(@ModelAttribute VoucherDto voucherDto, RedirectAttributes redirectAttributes) {
        UUID voucherId = voucherService.addVoucher(voucherDto.getVoucherType(), voucherDto.getAmount());
        redirectAttributes.addAttribute("voucherId", voucherId);
        redirectAttributes.addAttribute("isSave", true);
        return "redirect:/vouchers/{voucherId}";
    }

    @PostMapping("/{voucherId}/remove")
    public String removeVoucher(@PathVariable UUID voucherId) {
        if (validateRegisteredVoucher(voucherId)) {
            return "error/404";
        }

        voucherService.removeVoucher(voucherId);
        return "redirect:/vouchers";
    }

    private boolean validateRegisteredVoucher(UUID voucherId) {
        return !voucherService.isRegisteredVoucher(voucherId);
    }
}
