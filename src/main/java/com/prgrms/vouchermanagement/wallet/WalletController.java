package com.prgrms.vouchermanagement.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wallets")
public class WalletController {

    private final VoucherWalletService walletService;

    public WalletController(VoucherWalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<Wallet> wallets = walletService.findAll();
        model.addAttribute("wallets", WalletResponse.fromList(wallets));
        return "wallet/wallets";
    }

    @PostMapping("/{walletId}/remove")
    public String remove(@PathVariable Long walletId) {
        walletService.removeVoucherInWallet(walletId);
        return "redirect:/wallets";
    }

    @GetMapping("/add")
    public String addForm() {
        return "wallet/addForm";
    }

    @PostMapping("/add")
    public String addForm(@ModelAttribute WalletRequest walletRequest) {
        walletService.addVoucherToWallet(walletRequest.getCustomerId(), walletRequest.getVoucherId());
        return "redirect:/wallets";
    }
}