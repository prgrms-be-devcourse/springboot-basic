package org.prgrms.kdt.wallet.controller;

import org.prgrms.kdt.wallet.dto.CreateWalletRequest;
import org.prgrms.kdt.wallet.dto.WalletListResponse;
import org.prgrms.kdt.wallet.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void createWallet(CreateWalletRequest request) {
        walletService.assignVoucherToCustomer(request);
    }

    public WalletListResponse findVouchersByMemberId(UUID memberId) {
        return walletService.findVouchersByMemberId(memberId);
    }

    public WalletListResponse findMembersByVoucherId(UUID voucherId) {
        return walletService.findMembersByVoucherId(voucherId);
    }

    public void deleteWalletById(UUID walletId) {
        walletService.deleteWalletById(walletId);
    }

    public WalletListResponse findAllWallet() {
        return walletService.findAllWallet();
    }
}
