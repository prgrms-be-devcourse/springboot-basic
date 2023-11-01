package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.wallet.Wallet;
import com.prgrms.vouchermanager.dto.customer.CustomerResponse;
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

import static com.prgrms.vouchermanager.dto.customer.CustomerResponse.*;
import static com.prgrms.vouchermanager.dto.voucher.VoucherResponse.*;
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
                         Model model,
                         RedirectAttributes redirectAttributes) {
        WalletDetailResponse response = service.create(request);
        redirectAttributes.addAttribute("walletId", response.walletId());
        model.addAttribute("wallet", response);
        return "redirect: basic/wallets/{walletId}";
    }

    @GetMapping
    public String wallets(Model model) {
        List<WalletDetailResponse> response = service.findAll();
        model.addAttribute("wallets", response);
        return "basic/wallets/wallets";
    }

    @GetMapping("/{walletId}")
    public String findById(@PathVariable UUID walletId, Model model) {
        WalletDetailResponse response = service.findById(walletId);
        model.addAttribute("wallet", response);
        return "basic/wallets/wallet";
    }

    @GetMapping("/findByVoucherId")
    public String findByVoucherIdForm() {
        return "basic/wallets/findByVoucherId";
    }

    @GetMapping("/findByCustomerId")
    public String findByCustomerIdForm() {
        return "basic/wallets/findByCustomerId";
    }

    @GetMapping("/{customerId}/findByCustomerId")
    public String findByCustomerId(@PathVariable UUID customerId, Model model) {
        List<VoucherDetailResponse> response = service.findByCustomerId(customerId);
        model.addAttribute("vouchers", response);
        return "basic/wallets/vouchers";
    }

    @GetMapping("/{voucherId}/findByVoucherId")
    public String findByVoucherId(@PathVariable UUID voucherId, Model model) {
        List<CustomerDetailResponse> response = service.findByVoucherId(voucherId);
        model.addAttribute("customers", response);
        return "basic/wallets/customers";
    }

    @GetMapping("{walletId}/delete")
    public String delete(@PathVariable UUID walletId) {
        service.delete(walletId);
        return "basic/wallets/delete";
    }
}
