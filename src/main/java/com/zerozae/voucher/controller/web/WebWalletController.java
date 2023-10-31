package com.zerozae.voucher.controller.web;

import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.wallet.WalletCreateRequest;
import com.zerozae.voucher.dto.wallet.WalletResponse;
import com.zerozae.voucher.service.customer.CustomerService;
import com.zerozae.voucher.service.voucher.VoucherService;
import com.zerozae.voucher.service.wallet.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import static com.zerozae.voucher.validator.InputValidator.validateInputUuid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wallets")
public class WebWalletController {

    private final WalletService walletService;
    private final CustomerService customerService;
    private final VoucherService voucherService;

    @PostMapping
    public String createWallet(@Valid @ModelAttribute WalletCreateRequest walletCreateRequest){
        walletService.createWallet(walletCreateRequest);
        return "redirect:/wallets";
    }

    @GetMapping
    public String findAllWallets(Model model){
        List<WalletResponse> wallets = walletService.findAllWallets();
        model.addAttribute("wallets", wallets);
        return "/wallet/wallets";
    }

    @GetMapping("/createForm")
    public String getCreateForm(){
        return "/wallet/createForm";
    }

    @GetMapping("/customer/{customerId}")
    public String getCustomerDetails(@PathVariable("customerId") String customerId, Model model){
        CustomerResponse customer = validateCustomer(customerId);
        model.addAttribute("customer", customer);
        return "/wallet/customerDetails";
    }

    @GetMapping("/voucher/{voucherId}")
    public String getVoucherDetails(@PathVariable("voucherId") String voucherId, Model model){
        VoucherResponse voucher = validateVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "wallet/voucherDetails";
    }

    @DeleteMapping("/{customerId}/{voucherId}")
    public String deleteWallet(@PathVariable String customerId, @PathVariable String voucherId){
            validateCustomer(customerId);
            validateVoucher(voucherId);
            walletService.deleteWalletFromCustomer(UUID.fromString(customerId), UUID.fromString(voucherId));
            return "redirect:/wallets";
    }

    private CustomerResponse validateCustomer(String customerId){
        validateInputUuid(customerId);
        return customerService.findById(UUID.fromString(customerId));
    }

    private VoucherResponse validateVoucher(String voucherId){
        validateInputUuid(voucherId);
        return voucherService.findById(UUID.fromString(voucherId));
    }
}
