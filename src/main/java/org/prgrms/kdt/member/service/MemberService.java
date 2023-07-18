package org.prgrms.kdt.member.service;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.dao.MemberRepository;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.member.dto.CreateMemberRequest;
import org.prgrms.kdt.member.dto.MemberResponse;
import org.prgrms.kdt.member.dto.MemberResponses;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse createMember(CreateMemberRequest request) {
        Member member = new Member(request.name(), request.status());
        return new MemberResponse(memberRepository.insert(member));
    }

    public MemberResponses findAllBlackMember() {
        return MemberResponses.of(memberRepository.findByStatus(MemberStatus.BLACK));
    }

    public MemberResponses findAllMember() {
        return MemberResponses.of(memberRepository.findAll());
    }
}
