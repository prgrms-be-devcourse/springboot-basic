package org.prgrms.kdt.wallet.controller;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.wallet.dto.CreateWalletRequest;
import org.prgrms.kdt.wallet.service.WalletService;

import java.util.List;
import java.util.UUID;

public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void createWallet(CreateWalletRequest request){
        walletService.assignVoucherToCustomer(request);
    }

    public List<Voucher> findVouchersByMemberId(UUID memberId){
        return walletService.findVouchersByMemberId(memberId);
    }

    public List<Member> findMembersByVoucherId(UUID voucherId){
        return walletService.findMembersByVoucherId(voucherId);
    }

    public void deleteWalletByMemberId(UUID memberId){
        walletService.deleteWalletByMemberId(memberId);
    }
}
