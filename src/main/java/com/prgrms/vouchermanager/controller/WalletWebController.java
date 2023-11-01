package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.wallet.Wallet;
import com.prgrms.vouchermanager.dto.wallet.WalletRequest;
import com.prgrms.vouchermanager.dto.wallet.WalletResponse;
import com.prgrms.vouchermanager.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanager.dto.wallet.WalletRequest.*;
import static com.prgrms.vouchermanager.dto.wallet.WalletResponse.*;

@Controller
@RequestMapping("/basic/wallets")
@RequiredArgsConstructor
public class WalletWebController {

    private final WalletService service;

    @GetMapping("/create")
    public String createForm() {
        return "basic/wallets/createForm";
    }
    @PostMapping("/create")
    public String create(@RequestParam UUID customerId,
                         @RequestParam UUID voucherId,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        Wallet wallet = service.create(customerId, voucherId);
        redirectAttributes.addAttribute("walletId", wallet.getWalletId());
        model.addAttribute("customerId", customerId);
        model.addAttribute("voucherId", voucherId);
        return "redirect: basic/wallets/{walletId}";
    }

    public List<Voucher> findByCustomerId(UUID id) {
        return service.findByCustomerId(id);
    }

    public List<com.prgrms.vouchermanager.domain.customer.Customer> findByVoucherId(UUID id) {
        return service.findByVoucherId(id);
    }

    public int delete(UUID customerId, UUID voucherId) {
        return service.delete(customerId, voucherId);
    }
}
