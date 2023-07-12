package org.prgrms.kdt.wallet.controller;

import org.prgrms.kdt.wallet.dto.request.CreateWalletRequest;
import org.prgrms.kdt.wallet.dto.response.WalletResponse;
import org.prgrms.kdt.wallet.dto.response.WalletsResponse;
import org.prgrms.kdt.wallet.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.List;
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

    public WalletsResponse findVouchersByMemberId(UUID memberId) {
        return walletService.findVouchersByMemberId(memberId);
    }

    public WalletsResponse findMembersByVoucherId(UUID voucherId) {
        return walletService.findMembersByVoucherId(voucherId);
    }

    public void deleteWalletById(UUID walletId) {
        walletService.deleteWalletById(walletId);
    }

    public WalletsResponse findAllWallet() {
        return walletService.findAllWallet();
    }
}
