package org.prgms.kdtspringweek1.controller.thymeleafController;

import org.prgms.kdtspringweek1.customer.service.dto.FindCustomerResponseDto;
import org.prgms.kdtspringweek1.voucher.service.dto.FindVoucherResponseDto;
import org.prgms.kdtspringweek1.wallet.service.WalletService;
import org.prgms.kdtspringweek1.wallet.service.dto.CreateWalletRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class ThymeleafWalletController {

    private final WalletService walletService;

    public ThymeleafWalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallet")
    public String createWallet(@ModelAttribute CreateWalletRequestDto createWalletRequestDto) {
        walletService.registerWallet(createWalletRequestDto.toWallet());
        return "wallet/wallet-create";
    }

    @GetMapping("/customer/wallets")
    public String searchAllVouchersByCustomerId(@RequestParam String customerId, Model model) {
        List<FindVoucherResponseDto> vouchers = walletService.searchAllVouchersByCustomerId(UUID.fromString(customerId));
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("vouchers", vouchers);
        return "wallet/wallet-search";
    }

    @PostMapping("/wallet-delete")
    public String deleteWalletByVoucherIdAndCustomerId(@RequestParam String voucherId, @RequestParam String customerId) {
        walletService.deleteWalletByVoucherIdAndCustomerId(UUID.fromString(voucherId), UUID.fromString(customerId));
        return "wallet/wallet-delete";
    }

    @GetMapping("/voucher/wallets")
    public String searchAllCustomersByVoucherId(@RequestParam String voucherId, Model model) {
        List<FindCustomerResponseDto> customers = walletService.searchAllCustomersByVoucherId(UUID.fromString(voucherId));
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("customers", customers);
        return "wallet/wallet-search";
    }

    @GetMapping("/wallet")
    public String searchWallet() {
        return "wallet/wallet-search";
    }

    @GetMapping("/wallet-delete")
    public String deleteWallet() {
        return "wallet/wallet-delete";
    }

    @GetMapping("/wallet-create")
    public String createWallet() {
        return "wallet/wallet-create";
    }
}
