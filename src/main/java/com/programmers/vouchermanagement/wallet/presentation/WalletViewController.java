package com.programmers.vouchermanagement.wallet.presentation;

import com.programmers.vouchermanagement.customer.application.CustomerService;
import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.wallet.application.WalletService;
import com.programmers.vouchermanagement.wallet.dto.WalletRequestDto;
import com.programmers.vouchermanagement.wallet.dto.WalletResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/wallet")
public class WalletViewController {

    private final WalletService walletService;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public WalletViewController(WalletService walletService, VoucherService voucherService, CustomerService customerService) {
        this.walletService = walletService;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @GetMapping("/new")
    public String createWalletPage(Model model) {

        List<VoucherResponseDto> voucherResponseDtos = voucherService.readAllVoucher();
        List<CustomerResponseDto> customerResponseDtos = customerService.readAllBlackList();
        model.addAttribute("vouchers", voucherResponseDtos);
        model.addAttribute("customers", customerResponseDtos);

        return "wallet/wallet-form";
    }

    @PostMapping
    public String createWallet(WalletRequestDto walletRequestDto) {

        walletService.createWallet(walletRequestDto);

        return "redirect:/wallet";
    }

    @GetMapping
    public String readAllWallet(Model model) {

        List<WalletResponseDto> walletResponseDtos = walletService.readAllWallet();
        model.addAttribute("wallets", walletResponseDtos);

        return "wallet/wallet-list";
    }

    @GetMapping("/vouchers/{customerId}")
    public String readVouchersByCustomer(@PathVariable UUID customerId, Model model) {

        List<VoucherResponseDto> voucherResponseDtos = walletService.readVouchersByCustomer(customerId);
        model.addAttribute("vouchers", voucherResponseDtos);
        model.addAttribute("customerId", customerId);

        return "wallet/wallet-vouchers";
    }

    @GetMapping("/customers/{voucherId}")
    public String readCustomersByVoucher(@PathVariable UUID voucherId, Model model) {

        List<CustomerResponseDto> customerResponseDtos = walletService.readCustomersByVoucher(voucherId);
        model.addAttribute("customers", customerResponseDtos);

        return "wallet/wallet-customers";
    }

    @PostMapping("/vouchers/{customerId}")
    public String removeWalletsByCustomer(@PathVariable UUID customerId) {

        walletService.removeWalletsByCustomer(customerId);

        return "redirect:/wallet";
    }
}
