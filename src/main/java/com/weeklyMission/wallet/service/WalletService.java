package com.weeklyMission.wallet.service;

import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.member.repository.MemberRepository;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.repository.VoucherRepository;
import com.weeklyMission.wallet.domain.Wallet;
import com.weeklyMission.wallet.dto.WalletRequest;
import com.weeklyMission.wallet.repository.WalletRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final MemberRepository memberRepository;
    private final VoucherRepository voucherRepository;

    public WalletService(WalletRepository walletRepository,
        MemberRepository memberRepository,
        VoucherRepository voucherRepository) {
        this.walletRepository = walletRepository;
        this.memberRepository = memberRepository;
        this.voucherRepository = voucherRepository;
    }

    public void save(WalletRequest wallet){
        walletRepository.save(wallet.toEntity());
    }

    public List<VoucherResponse> findByMember(String memberId){
        List<String> voucherIdList = new ArrayList<>(walletRepository.findByMemberId(memberId).stream()
            .map(Wallet::voucherId)
            .toList());

        return voucherRepository.findByIds(voucherIdList)
            .stream()
            .map(VoucherResponse::of)
            .toList();
    }

    public List<MemberResponse> findByVoucher(String voucherId){
        List<String> memberIdList = new ArrayList<>(walletRepository.findByVoucherId(voucherId).stream()
            .map(Wallet::memberId)
            .toList());

        return memberRepository.findByIds(memberIdList)
            .stream()
            .map(MemberResponse::of)
            .toList();
    }

    public void deleteById(String memberId, String voucherId){
        walletRepository.deleteById(memberId, voucherId);
    }
}
