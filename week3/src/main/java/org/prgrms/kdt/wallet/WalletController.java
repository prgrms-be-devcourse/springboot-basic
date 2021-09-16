package org.prgrms.kdt.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/wallets")
    public String viewNewCustomerPage(@RequestParam("walletId") UUID walletId, @RequestParam("customerId") UUID customerId, Model model) {
        model.addAttribute("customerId", customerId);
        model.addAttribute("walletId", walletId);
        return "/new-vouchers";
    }
}
