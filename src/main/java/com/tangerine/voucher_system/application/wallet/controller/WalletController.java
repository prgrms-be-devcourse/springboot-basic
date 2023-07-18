package com.tangerine.voucher_system.application.wallet.controller;

import com.tangerine.voucher_system.application.voucher.controller.VoucherDto;
import com.tangerine.voucher_system.application.wallet.model.Wallet;
import com.tangerine.voucher_system.application.wallet.service.WalletService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void createWallet(Wallet wallet) {
        walletService.createWallet(wallet);
    }

    public void updateWallet(Wallet wallet) {
        walletService.updateWallet(wallet);
    }

    public void deleteWalletById(UUID walletId) {
        walletService.deleteWalletById(walletId);
    }

    public List<VoucherDto> voucherListOfCustomer(UUID customerId) {
        return walletService.findVouchersByCustomerId(customerId).stream()
                .map(VoucherDto::of)
                .toList();
    }

    public List<CustomerDto> customerListHasVoucher(UUID voucherId) {
        return walletService.findCustomersByVoucherId(voucherId).stream()
                .map(CustomerDto::of)
                .toList();
    }

}
