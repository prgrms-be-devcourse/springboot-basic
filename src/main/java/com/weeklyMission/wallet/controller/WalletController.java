package com.weeklyMission.wallet.controller;

import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.wallet.dto.WalletRequest;
import com.weeklyMission.wallet.service.WalletService;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void walletSave(WalletRequest wallet){
        walletService.save(wallet);
    }

    public List<VoucherResponse> findByMemberId(String memberId){
        return walletService.findByMember(memberId);
    }

    public List<MemberResponse> findByVoucherId(String voucherId){
        return walletService.findByVoucher(voucherId);
    }

    public void deleteById(WalletRequest wallet){
        walletService.deleteById(wallet);
    }
}
