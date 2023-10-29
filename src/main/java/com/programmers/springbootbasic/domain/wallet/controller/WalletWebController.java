package com.programmers.springbootbasic.domain.wallet.controller;

import com.programmers.springbootbasic.domain.customer.vo.Email;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import com.programmers.springbootbasic.domain.wallet.dto.WalletControllerRequestDto;
import com.programmers.springbootbasic.domain.wallet.dto.WalletServiceRequestDto;
import com.programmers.springbootbasic.domain.wallet.entity.Wallet;
import com.programmers.springbootbasic.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wallets")
public class WalletWebController {

    private final WalletService walletService;
    private final VoucherService voucherService;

    @GetMapping("/addForm")
    public String addWalletForm(Model model) {
        model.addAttribute("vouchers", voucherService.findAllVouchers());
        return "wallets/addForm";
    }

    @GetMapping("/deleteForm")
    public String removeWalletForm() {
        return "wallets/deleteForm";
    }

    @GetMapping("/findVoucherForm")
    public String findAllVoucherForm() {
        return "wallets/findVoucherByCustomerForm";
    }

    @GetMapping("/findCustomerForm")
    public String findAllCustomerForm(Model model) {
        model.addAttribute("vouchers", voucherService.findAllVouchers());
        return "wallets/findCustomerByVoucherForm";
    }

    @PostMapping("/add")
    public String addWallet(WalletControllerRequestDto walletControllerRequestDto) {
        walletService.createWallet(WalletServiceRequestDto.builder()
                .email(Email.from(walletControllerRequestDto.getEmail()).getValue())
                .voucherId(UUID.fromString(walletControllerRequestDto.getVoucherId()))
                .build());
        return "redirect:/";
    }

    @DeleteMapping("/delete")
    public String removeWallet(WalletControllerRequestDto walletControllerRequestDto) {
        walletService.deleteWallet(WalletServiceRequestDto.builder()
                .voucherId(UUID.fromString(walletControllerRequestDto.getVoucherId()))
                .email(Email.from(walletControllerRequestDto.getEmail()).getValue())
                .build());
        return "redirect:/";
    }

    @GetMapping("/voucher")
    public String showAllVouchersInWallet(@RequestParam String email, Model model) {
        List<Wallet> wallets = walletService.findWalletsByCustomerEmail(WalletServiceRequestDto.builder()
                .email(Email.from(email).getValue())
                .build());
        model.addAttribute("email", email);
        model.addAttribute("wallets", wallets);
        return "wallets/voucher";
    }

    @GetMapping("/customer")
    public String showAllCustomerHasVoucher(@RequestParam String voucherId, Model model) {
        List<Wallet> wallets = walletService.findWalletsByVoucherId(WalletServiceRequestDto.builder()
                .voucherId(UUID.fromString(voucherId))
                .build());
        model.addAttribute("wallets", wallets);
        return "wallets/customer";
    }
}
