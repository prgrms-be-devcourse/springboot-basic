package com.programmers.vouchermanagement.wallet.controller;

import com.programmers.vouchermanagement.customer.controller.dto.CustomerResponse;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import com.programmers.vouchermanagement.wallet.service.WalletService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

@Profile({"console & jdbc"})
@Controller
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void allocate(Ownership ownership) {
        walletService.allocate(ownership);
    }

    public CustomerResponse readCustomerByVoucherId(UUID voucherId, Model model) {
        return walletService.readCustomerByVoucherId(voucherId);
    }

    public List<VoucherResponse> readAllVoucherByCustomerId(UUID customerId, Model model) {
        return walletService.readAllVoucherByCustomerId(customerId);
    }

    public void deleteAllAllocation() {
        walletService.deleteAllAllocation();
    }
}
