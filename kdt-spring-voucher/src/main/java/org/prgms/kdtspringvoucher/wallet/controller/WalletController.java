package org.prgms.kdtspringvoucher.wallet.controller;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.wallet.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public String getWalletMain() {
        return "wallet/wallet-main";
    }

    @GetMapping("assign")
    public String getCustomerToAssign(Model model) {
        List<Customer> customers = walletService.getCustomers();
        model.addAttribute("customers", customers);
        return "wallet/wallet-assign-customers";
    }

    @GetMapping("assign/{customerId}")
    public String getVoucherToAssignToCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        List<Voucher> vouchers = walletService.getVoucherToAssign();
        model.addAttribute("vouchers", vouchers);
        model.addAttribute("customerId", customerId);
        return "wallet/wallet-assign-vouchers";
    }

    @GetMapping("assign/{customerId}/{voucherId}")
    public String assignVoucherToCustomer(@PathVariable("customerId") UUID customerId, @PathVariable("voucherId") UUID voucherId) {
        walletService.assignVoucherToCustomer(customerId, voucherId);
        return "redirect:/wallet";
    }

    @GetMapping("list")
    public String getCustomersToList(Model model) {
        List<Customer> customers = walletService.getCustomers();
        model.addAttribute("customers", customers);
        return "wallet/wallet-list-customers";
    }

    @GetMapping("list/{customerId}")
    public String getVoucherAssignedToCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        List<Voucher> vouchers = walletService.getVouchersByCustomerId(customerId);
        model.addAttribute("vouchers", vouchers);
        return "wallet/wallet-list-vouchers";
    }

    @GetMapping("delete/{voucherId}")
    public String deleteVoucherAssignedToCustomer(@PathVariable("voucherId") UUID voucherId) {
        walletService.deleteVoucherAssignedToCustomer(voucherId);
        return "redirect:/wallet";
    }
}
