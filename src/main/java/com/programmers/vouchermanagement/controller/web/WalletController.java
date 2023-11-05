package com.programmers.vouchermanagement.controller.web;

import com.programmers.vouchermanagement.dto.VoucherDto;
import com.programmers.vouchermanagement.service.CustomerService;
import com.programmers.vouchermanagement.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;
    private final CustomerService customerService;

    public WalletController(WalletService walletService, CustomerService customerService) {
        this.walletService = walletService;
        this.customerService = customerService;
    }

    @GetMapping
    public String showWallet(@RequestParam String customerId, Model model) {
        String customerName = customerService.findCustomerNameById(UUID.fromString(customerId));
        List<VoucherDto.Response> vouchers = walletService.findVoucherByCustomer(UUID.fromString(customerId)).stream()
                .map(VoucherDto.Response::new).toList();
        model.addAttribute("customerName", customerName);
        model.addAttribute("vouchers", vouchers);
        return "/voucher/voucher_detail";
    }

    @GetMapping("/new")
    public String getCustomerIdAndVoucherId() {
        return "/wallet/add_wallet_form";
    }

    @PostMapping("/new")
    public String giveVoucherToCustomer(@RequestParam String customerId, @RequestParam String voucherId) {
        walletService.giveVoucherToCustomer(UUID.fromString(customerId), UUID.fromString(voucherId));
        return "redirect:/wallet?customerId=" + customerId;
    }
}
