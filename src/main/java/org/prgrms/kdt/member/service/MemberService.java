package org.prgrms.kdt.member.service;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.dao.MemberRepository;
import org.prgrms.kdt.member.dto.CreateMemberRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(CreateMemberRequest request) {
        Member member = new Member(request.name(), request.status());
        return memberRepository.insert(member);
    }

    public List<Member> findAllBlackMember() {
        return memberRepository.findAllBlackMember();
    }

    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }
}
