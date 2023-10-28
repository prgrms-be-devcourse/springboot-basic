package com.weeklyMission.wallet.service;

import com.weeklyMission.member.domain.Member;
import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.member.repository.DBMemberRepository;
import com.weeklyMission.member.service.MemberService;
import com.weeklyMission.voucher.domain.Voucher;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.repository.DBVoucherRepository;
import com.weeklyMission.voucher.service.VoucherService;
import com.weeklyMission.wallet.domain.Wallet;
import com.weeklyMission.wallet.repository.WalletRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final DBMemberRepository memberRepository;
    private final DBVoucherRepository voucherRepository;

    public WalletService(WalletRepository walletRepository,
        DBMemberRepository memberRepository,
        DBVoucherRepository voucherRepository) {
        this.walletRepository = walletRepository;
        this.memberRepository = memberRepository;
        this.voucherRepository = voucherRepository;
    }

    public void save(String memberId, String voucherId){
        Wallet wallet = new Wallet(UUID.randomUUID().toString(), memberId, voucherId);
        walletRepository.save(wallet);
    }

    public List<VoucherResponse> findByMemberId(String memberId){
        List<String> voucherIdList = walletRepository.findByMemberId(memberId).stream()
            .map(Wallet::voucherId)
            .toList();

        return voucherRepository.findByIds(voucherIdList).stream()
            .map(VoucherResponse::of)
            .toList();
    }

    public List<MemberResponse> findByVoucherId(String voucherId){
        List<String> memberIdList = walletRepository.findByVoucherId(voucherId).stream()
            .map(Wallet::memberId)
            .toList();

        return memberRepository.findByIds(memberIdList).stream()
            .map(MemberResponse::of)
            .toList();
    }

    public void deleteById(String memberId, String voucherId){
        walletRepository.deleteById(memberId, voucherId);
    }
}
