package org.prgrms.kdt.member.service;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.dao.MemberRepository;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.member.dto.CreateMemberRequest;
import org.prgrms.kdt.member.dto.MemberResponse;
import org.prgrms.kdt.member.dto.MembersResponse;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public MembersResponse findAllBlackMember() {
        return MembersResponse.of(memberRepository.findByStatus(MemberStatus.BLACK));
    }

    public MembersResponse findAllMember() {
        return MembersResponse.of(memberRepository.findAll());
    }
}
