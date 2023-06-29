package org.prgrms.kdt.member.service;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAllBlackMember() {
        return memberRepository.findAllBlackMember();
    }
}
