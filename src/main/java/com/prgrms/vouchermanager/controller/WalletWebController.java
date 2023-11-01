package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.wallet.Wallet;
import com.prgrms.vouchermanager.dto.voucher.VoucherResponse;
import com.prgrms.vouchermanager.dto.wallet.WalletRequest;
import com.prgrms.vouchermanager.dto.wallet.WalletResponse;
import com.prgrms.vouchermanager.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanager.dto.voucher.VoucherResponse.toDetailVoucher;
import static com.prgrms.vouchermanager.dto.wallet.WalletRequest.*;
import static com.prgrms.vouchermanager.dto.wallet.WalletResponse.*;

@Controller
@Slf4j
@RequestMapping("/basic/wallets")
@RequiredArgsConstructor
public class WalletWebController {

    private final WalletService service;

    @GetMapping("/create")
    public String createForm() {
        return "basic/wallets/createForm";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute WalletDetailRequest request,
                         Model model) {
        Wallet wallet = service.create(request.customerId(), request.voucherId());
        return "redirect: basic/wallets/wallets";
    }

    @GetMapping
    public String wallets(Model model) {
        WalletListResponse response = new WalletListResponse(service.findAll());
        log.info("wallet : " + service.findAll().get(0));
        model.addAttribute("wallets", response.wallets());
        return "basic/wallets/wallets";
    }

    @GetMapping("/{walletId}")
    public String findById(@PathVariable UUID walletId, Model model) {
        WalletDetailResponse response = toDetailWallet(service.findById(walletId));
        model.addAttribute("voucherId", response.voucherId());
        model.addAttribute("customerId", response.customerId());
        model.addAttribute("walletId", response.walletId());
        return "basic/wallets/wallet";
    }

    @GetMapping("/findByVoucherId")
    public String findByVoucherIdForm() {
        return "basic/wallets/findByVoucherId";
    }

    @GetMapping("/findByVoucherIdMid")
    public String findByVoucherIdMid(@RequestParam UUID voucherId,
                                     RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("voucherId", voucherId);
        return "redirect:/basic/wallets/{voucherId}/findByVoucherId";
    }

    @GetMapping("/findByCustomerId")
    public String findByCustomerIdForm() {
        return "basic/wallets/findByCustomerId";
    }

    @GetMapping("/{customerId}/findByCustomerId")
    public String findByCustomerId(@PathVariable UUID customerId, Model model) {
        List<Voucher> byCustomerId = service.findByCustomerId(customerId);
        WalletVoucherListResponse response = new WalletVoucherListResponse(byCustomerId);
        model.addAttribute("vouchers", response.vouchers());
        return "basic/wallets/vouchers";
    }

    @GetMapping("/{voucherId}/findByVoucherId")
    public String findByVoucherId(@PathVariable UUID voucherId, Model model) {
        List<Customer> byVoucherId = service.findByVoucherId(voucherId);
        WalletCustomerListResponse response = new WalletCustomerListResponse(byVoucherId);
        model.addAttribute("customers", response.customers());
        return "basic/wallets/customers";
    }

    @PostMapping("{walletId}/delete")
    public String delete(@PathVariable UUID walletId) {
        service.delete(walletId);
        return "basic/wallets/wallets";
    }
}
