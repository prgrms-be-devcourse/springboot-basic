package org.prgrms.voucherapp.engine.wallet.controller;

import org.prgrms.voucherapp.engine.customer.entity.Customer;
import org.prgrms.voucherapp.engine.customer.service.CustomerService;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.prgrms.voucherapp.engine.wallet.dto.WalletDto;
import org.prgrms.voucherapp.engine.wallet.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {

    private final WalletService walletService;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public WalletController(WalletService walletService, VoucherService voucherService, CustomerService customerService) {
        this.walletService = walletService;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @GetMapping("/wallets/{voucherId}")
    public String walletsViewByVoucher(@PathVariable UUID voucherId, Model model){
        List<Customer> customers = walletService.getCustomersOfVoucherById(voucherId);
        model.addAttribute("customers", customers);
        return "wallets-by-voucher";
    }

    @PostMapping("/wallets/delete")
    public String deleteWalletByBoth(WalletDto deleteDto){
        walletService.removeByBothId(deleteDto.voucherId(), deleteDto.customerId());
        return "redirect:/wallets/"+deleteDto.voucherId();
    }

    @PostMapping("/wallets/create")
    public String createWallet(WalletDto createDto){
        walletService.assignVoucherToCustomer(UUID.randomUUID(),createDto.customerId(), createDto.voucherId());
        return "redirect:/wallets/"+createDto.voucherId();
    }

}
