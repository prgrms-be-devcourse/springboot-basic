package com.programmers.vouchermanagement.wallet.controller;

import com.programmers.vouchermanagement.customer.dto.CustomerDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import com.programmers.vouchermanagement.wallet.service.WalletService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Profile("mvc")
@RequestMapping("/wallets")
@Controller
public class WalletMVCController {
    private final WalletService walletService;

    public WalletMVCController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public void allocate(Ownership ownership) {
        walletService.allocate(ownership);
    }

    @GetMapping("/vouchers/{voucherId}")
    public String readCustomerByVoucherId(@PathVariable("voucherId") UUID voucherId, Model model) {
        CustomerDto customer = walletService.readCustomerByVoucherId(voucherId);
        model.addAttribute("customers", List.of(customer));
        return "views/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String readAllVoucherByCustomerId(@PathVariable("customerId") UUID customerId, Model model) {
        List<VoucherDto> vouchers = walletService.readAllVoucherByCustomerId(customerId);
        model.addAttribute("vouchers", List.of(vouchers));
        return "views/vouchers";
    }

    @DeleteMapping
    public String deleteAllAllocation() {
        walletService.deleteAllAllocation();
        return "redirect:/wallets";
    }
}
