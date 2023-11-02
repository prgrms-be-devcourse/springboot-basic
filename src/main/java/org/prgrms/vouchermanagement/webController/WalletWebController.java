package org.prgrms.vouchermanagement.webController;

import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.wallet.domain.WalletMapper;
import org.prgrms.vouchermanagement.wallet.service.WalletMapperService;
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
public class WalletWebController {

    private final WalletMapperService walletMapperService;

    public WalletWebController(WalletMapperService walletMapperService) {
        this.walletMapperService = walletMapperService;
    }

    @GetMapping("/wallet-home")
    public String walletHome() {
        return "/wallet/wallet-home";
    }

    @GetMapping("/create")
    public String createWallet() {
        return "wallet/create";
    }

    @PostMapping("/create")
    public String createWallet(@RequestParam("customerId") String customerId, @RequestParam("voucherId") String voucherId) {
        walletMapperService.create(new WalletCreateInfo(UUID.fromString(customerId), UUID.fromString(voucherId)));

        return "redirect:/wallet/create";
    }

    @GetMapping("/list")
    public String voucherList(Model model) {
        List<WalletMapper> wallets = walletMapperService.findAll();
        model.addAttribute("wallets", wallets);
        return "wallet/list";
    }

    @GetMapping("/delete")
    public String deleteWallet() {
        return "wallet/delete";
    }

    @PostMapping("/delete")
    public String createWallet(@RequestParam("customerId") String customerId) {
        walletMapperService.delete(UUID.fromString(customerId));

        return "redirect:/wallet/delete";
    }

}
