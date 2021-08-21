package org.prgrms.kdt.member.application;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void init(List<String> lines) {
        memberRepository.init(lines);
    }

    public Map<UUID, Member> allVoucher() {
        return memberRepository.findByAllBlackList();
    }

}
