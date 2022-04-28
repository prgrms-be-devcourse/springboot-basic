package com.example.voucherproject.wallet;

import com.example.voucherproject.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    // 지갑 목록 조회
    @GetMapping("/wallets")
    public String walletsView(Model model){
        model.addAttribute("wallets", walletService.findAll());
        model.addAttribute("userMap",walletService.findUserMap());
        model.addAttribute("voucherMap", walletService.findVoucherMap());

        return "wallet/wallets";
    }

    // 지갑 상세 조회
    @GetMapping("/wallet/{id}")
    public String voucherDetailView(@PathVariable UUID id, Model model){
        var maybeWallet = walletService.findById(id);

        if (maybeWallet.isPresent()){
            model.addAttribute("wallet", maybeWallet.get());
            return "wallet/wallet-detail";
        }
        else{
            return "basic/404";
        }
    }

    // 지갑 생성 - 뷰
    @GetMapping("/wallet/new")
    public String addWalletView(Model model){
        model.addAttribute("users",  walletService.findAllUsers());
        model.addAttribute("vouchers",  walletService.findAllVouchers());
        return "wallet/new-wallet";
    }
    // 지갑 생성
    @PostMapping("/wallet/new")
    public String addWalletRedirect(@RequestParam UUID userId, @RequestParam UUID voucherId){
        walletService.create(userId, voucherId);
        return "redirect:../wallets/";
    }

    // 지갑 삭제
    @PostMapping("/wallet/{id}")
    public String deleteWallet(@PathVariable UUID id){
        walletService.deleteById(id);
        return "redirect:../wallets/";
    }
}
