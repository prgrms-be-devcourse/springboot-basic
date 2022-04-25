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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final VoucherService voucherService;

    public WalletController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String walletPage() {
        return "wallet/wallet";
    }

    @GetMapping("/assign")
    public String walletAssignPage() {
        return "wallet/assign";
    }

    @PostMapping("/assign")
    public String assignVoucher(WalletRequest walletRequest) {
        voucherService.assignVoucher(walletRequest);
        return "redirect:/wallet";
    }

    @GetMapping("/vouchers")
    public String walletVouchersPage() {
        return "wallet/vouchers";
    }

    @PostMapping("/vouchers")
    public String assignVouchers(@RequestParam("customerEmail") Email customerEmail, Model model) {
        List<VoucherDto> vouchers = voucherService.findAssignVouchers(customerEmail).stream()
            .map(VoucherDto::from)
            .toList();
        model.addAttribute("vouchers", vouchers);
        model.addAttribute("email", customerEmail);
        return "wallet/vouchers";
    }

    @GetMapping("/vouchers/delete/{voucherId}")
    public String deleteAssignVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.delete(voucherId);
        return "redirect:/wallet/show";
    }

    @GetMapping("/customer")
    public String voucherIdForm() {
        return "wallet/customer";
    }

    @PostMapping("/customer")
    public String findCustomer(@RequestParam("voucherId") UUID voucherId, Model model) {
        Customer customer = voucherService.findCustomer(voucherId);
        model.addAttribute("customer", customer);
        return "wallet/customer";
    }

    @ExceptionHandler
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
