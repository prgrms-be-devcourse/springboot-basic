package com.weeklyMission.wallet.controller;

import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.wallet.service.WalletService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void walletSave(String memberId, String voucherId){
        walletService.save(memberId, voucherId);
    }

    public List<VoucherResponse> findByMemberId(String memberId){
        return walletService.findByMemberId(memberId);
    }

    public List<MemberResponse> findByVoucherId(String voucherId){
        return walletService.findByVoucherId(voucherId);
    }

    public void deleteById(String memberId, String voucherId){
        walletService.deleteById(memberId, voucherId);
    }
}
