package com.prgrms.springbasic.domain.wallet.controller;

import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.wallet.dto.CreateWalletRequest;
import com.prgrms.springbasic.domain.wallet.dto.WalletResponse;
import com.prgrms.springbasic.domain.wallet.service.WalletService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public WalletResponse createWallet(CreateWalletRequest request) {
        return walletService.saveWallet(request);
    }

    public List<VoucherResponse> findVouchersByCustomerId(UUID customerId) {
        return walletService.getVouchersByCustomerId(customerId);
    }

    public List<CustomerResponse> findCustomersByVoucherId(UUID voucherId) {
        return walletService.getCustomersByVoucherId(voucherId);
    }
}
