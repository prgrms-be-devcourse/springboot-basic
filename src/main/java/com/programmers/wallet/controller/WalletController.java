package com.programmers.wallet.controller;

import com.programmers.customer.dto.CustomerDto;
import com.programmers.customer.service.CustomerService;
import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.service.VoucherService;
import com.programmers.wallet.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class WalletController {
    private final WalletService walletService;
    private final CustomerService customerService;
    private final VoucherService voucherService;

    public WalletController(WalletService walletService, CustomerService customerService, VoucherService voucherService) {
        this.walletService = walletService;
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @GetMapping("/assign/{customerId}")
    public String assignPage(@PathVariable UUID customerId, Model model) {
        CustomerDto customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        List<VoucherDto> vouchers = voucherService.findAll();

        model.addAttribute("vouchers", vouchers);

        return "/customer/assignVoucher";
    }

    @PostMapping("/assign/{customerId}/{voucherId}")
    public String assign(@PathVariable UUID customerId, @PathVariable UUID voucherId) {
        walletService.assignVoucher(customerId, voucherId);

        return "redirect:/customers/" + customerId;
    }

    @PostMapping("/{customerId}/{voucherId}")
    public String delete(@PathVariable UUID customerId, @PathVariable UUID voucherId) {
        walletService.removeCustomerVoucher(customerId, voucherId);

        return "redirect:/customers/" + customerId;
    }
}
