package com.programmers.springweekly.controller;

import com.programmers.springweekly.dto.wallet.request.WalletCreateRequest;
import com.programmers.springweekly.dto.wallet.response.WalletResponse;
import com.programmers.springweekly.dto.wallet.response.WalletsResponse;
import com.programmers.springweekly.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    public void save(WalletCreateRequest walletCreateRequest) {
        walletService.save(walletCreateRequest);
    }

    public WalletResponse findByCustomerId(String customerId) {
        return walletService.findByCustomerId(customerId);
    }

    public WalletsResponse findByVoucherId(String voucherId) {
        return walletService.findByVoucherId(voucherId);
    }

    public void deleteByWalletId(String walletId) {
        walletService.deleteByWalletId(walletId);
    }

    public WalletsResponse findAll() {
        return walletService.findAll();
    }
}
