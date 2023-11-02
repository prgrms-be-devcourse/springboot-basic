package com.programmers.vouchermanagement.wallet.presentation;

import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.wallet.application.WalletService;
import com.programmers.vouchermanagement.wallet.dto.WalletRequestDto;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void createWallet(WalletRequestDto walletRequestDto) {
        walletService.createWallet(walletRequestDto);
    }

    public List<VoucherResponseDto> readVouchersByCustomer(UUID customerId) {
        return walletService.readVouchersByCustomer(customerId);
    }

    public List<CustomerResponseDto> readCustomersByVoucher(UUID voucherId) {
        return walletService.readCustomersByVoucher(voucherId);
    }

    public void removeWalletsByCustomer(UUID customerId) {
        walletService.removeWalletsByCustomer(customerId);
    }
}
