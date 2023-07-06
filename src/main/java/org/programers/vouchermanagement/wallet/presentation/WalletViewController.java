package org.programers.vouchermanagement.wallet.presentation;

import lombok.RequiredArgsConstructor;
import org.programers.vouchermanagement.member.application.MemberService;
import org.programers.vouchermanagement.member.dto.response.MembersResponse;
import org.programers.vouchermanagement.voucher.application.VoucherService;
import org.programers.vouchermanagement.voucher.dto.response.VouchersResponse;
import org.programers.vouchermanagement.wallet.application.WalletService;
import org.programers.vouchermanagement.wallet.dto.request.WalletCreationRequest;
import org.programers.vouchermanagement.wallet.dto.response.WalletResponse;
import org.programers.vouchermanagement.wallet.dto.response.WalletViewResponse;
import org.programers.vouchermanagement.wallet.dto.response.WalletsResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@RequestMapping("/view/wallets")
@RequiredArgsConstructor
@Controller
public class WalletViewController {

    private final WalletService walletService;
    private final VoucherService voucherService;
    private final MemberService memberService;

    @GetMapping
    public String save(Model model) {
        VouchersResponse vouchers = voucherService.findAll();
        MembersResponse members = memberService.findAll();
        model.addAttribute("vouchers", vouchers);
        model.addAttribute("members", members);
        model.addAttribute("wallet", new WalletViewResponse());
        return "wallets/saveWallet";
    }

    @PostMapping
    public String save(@ModelAttribute WalletCreationRequest request, RedirectAttributes redirectAttributes) {
        WalletResponse response = walletService.save(request);
        redirectAttributes.addAttribute("id", response.getId());
        return "redirect:/view/wallets/{id}";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable UUID id, Model model) {
        WalletResponse response = walletService.findById(id);
        model.addAttribute("wallet", response);
        return "wallets/wallet";
    }

    @GetMapping("/all")
    public String findAll(Model model) {
        WalletsResponse response = walletService.findAll();
        model.addAttribute("wallets", response);
        return "wallets/wallets";
    }

    @PostMapping("/{id}")
    public String deleteById(@PathVariable UUID id) {
        walletService.deleteById(id);
        return "redirect:/view/wallets/all";
    }
}
