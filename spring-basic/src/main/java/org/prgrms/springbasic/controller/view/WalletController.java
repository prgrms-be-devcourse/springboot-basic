package org.prgrms.springbasic.controller.view;

import lombok.RequiredArgsConstructor;
import org.prgrms.springbasic.service.web.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    public String showWallets(Model model) {
        var wallets = walletService.findWallets();
        model.addAttribute("wallets", wallets);

        return "/wallet/wallets";
    }
}
