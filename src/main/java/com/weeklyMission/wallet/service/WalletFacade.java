package com.weeklyMission.wallet.service;

import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.member.repository.MemberRepository;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.repository.VoucherRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class WalletFacade {

    private final MemberRepository memberRepository;
    private final VoucherRepository voucherRepository;

    public WalletFacade(MemberRepository memberRepository, VoucherRepository voucherRepository) {
        this.memberRepository = memberRepository;
        this.voucherRepository = voucherRepository;
    }

    public List<VoucherResponse> findVoucher(List<String> voucherIdList){
        return voucherRepository.findByIds(voucherIdList)
            .stream()
            .map(VoucherResponse::of)
            .toList();
    }

    public List<MemberResponse> findMember(List<String> memberIdList){
        return memberRepository.findByIds(memberIdList)
            .stream()
            .map(MemberResponse::of)
            .toList();
    }
}
