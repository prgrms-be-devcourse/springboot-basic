package org.prgrms.kdt.wallet.controller;

import org.prgrms.kdt.wallet.dto.request.CreateWalletRequest;
import org.prgrms.kdt.wallet.dto.response.JoinedWalletsResponse;
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

    public JoinedWalletsResponse findVouchersByMemberId(UUID memberId) {
        return walletService.findVouchersByMemberId(memberId);
    }

    public JoinedWalletsResponse findMembersByVoucherId(UUID voucherId) {
        return walletService.findMembersByVoucherId(voucherId);
    }

    public void deleteWalletById(UUID walletId) {
        walletService.deleteWalletById(walletId);
    }

    public JoinedWalletsResponse findAllWallet() {
        return walletService.findAllWallet();
    }
}
