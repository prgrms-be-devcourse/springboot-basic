package com.programmers.vouchermanagement.wallet.controller;

import com.programmers.vouchermanagement.customer.dto.CustomerDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import com.programmers.vouchermanagement.wallet.service.WalletService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

@Profile("console")
@Controller
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void allocate(Ownership ownership) {
        walletService.allocate(ownership);
    }

    public CustomerDto readCustomerByVoucherId(UUID voucherId, Model model) {
        return walletService.readCustomerByVoucherId(voucherId);
    }

    public List<VoucherDto> readAllVoucherByCustomerId(UUID customerId, Model model) {
        return walletService.readAllVoucherByCustomerId(customerId);
    }

    public void deleteAllAllocation() {
        walletService.deleteAllAllocation();
    }
}
