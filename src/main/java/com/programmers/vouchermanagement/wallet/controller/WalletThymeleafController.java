package com.programmers.vouchermanagement.wallet.controller;

import com.programmers.vouchermanagement.customer.controller.dto.CustomerResponse;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import com.programmers.vouchermanagement.wallet.service.WalletService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Profile("thyme")
@RequestMapping("/wallets")
@Controller
public class WalletThymeleafController {
    private final WalletService walletService;

    public WalletThymeleafController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public void allocate(Ownership ownership) {
        walletService.allocate(ownership);
    }

    @GetMapping("/vouchers/{voucherId}")
    public String readCustomerByVoucherId(@PathVariable("voucherId") UUID voucherId, Model model) {
        CustomerResponse customer = walletService.readCustomerByVoucherId(voucherId);
        model.addAttribute("customers", List.of(customer));
        return "views/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String readAllVoucherByCustomerId(@PathVariable("customerId") UUID customerId, Model model) {
        List<VoucherResponse> vouchers = walletService.readAllVoucherByCustomerId(customerId);
        model.addAttribute("vouchers", List.of(vouchers));
        return "views/vouchers";
    }

    @DeleteMapping
    public String deleteAllAllocation() {
        walletService.deleteAllAllocation();
        return "redirect:/wallets";
    }
}
