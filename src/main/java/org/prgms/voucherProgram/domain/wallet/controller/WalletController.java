package org.prgms.voucherProgram.domain.wallet.controller;

import java.util.List;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.domain.Customer;
import org.prgms.voucherProgram.domain.customer.domain.Email;
import org.prgms.voucherProgram.domain.voucher.dto.VoucherDto;
import org.prgms.voucherProgram.domain.voucher.service.VoucherService;
import org.prgms.voucherProgram.domain.wallet.dto.WalletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WalletController {

    private final VoucherService voucherService;

    public WalletController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public String homePage() {
        return "/home";
    }

    @GetMapping("/wallet")
    public String walletPage() {
        return "wallet/wallet";
    }

    @GetMapping("/wallet/assign")
    public String walletAssignPage() {
        return "wallet/assign";
    }

    @PostMapping("/wallet/assign")
    public String assignVoucher(WalletRequest walletRequest) {
        voucherService.assignVoucher(walletRequest);
        return "redirect:/wallet";
    }

    @GetMapping("/wallet/show")
    public String walletVouchersPage() {
        return "wallet/customerEmailForm";
    }

    @PostMapping("/wallet/vouchers")
    public String assignVouchers(@RequestParam Email customerEmail, Model model) {
        List<VoucherDto> vouchers = voucherService.findAssignVouchers(customerEmail).stream()
            .map(VoucherDto::from)
            .toList();
        model.addAttribute("vouchers", vouchers);
        model.addAttribute("email", customerEmail);
        return "wallet/vouchers";
    }

    @GetMapping("/wallet/vouchers/delete/{voucherId}")
    public String deleteAssignVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.delete(voucherId);
        return "redirect:/wallet/show";
    }

    @GetMapping("wallet/customer")
    public String voucherIdForm() {
        return "wallet/voucherIdForm";
    }

    @PostMapping("wallet/customer")
    public String findCustomer(@RequestParam("voucherId") UUID voucherId, Model model) {
        Customer customer = voucherService.findCustomer(voucherId);
        model.addAttribute("customer", customer);
        return "wallet/customer";
    }
}
