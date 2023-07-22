package org.prgrms.kdt.member.service;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.dao.MemberRepository;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.member.service.dto.CreateMemberServiceRequest;
import org.prgrms.kdt.member.service.dto.MemberResponse;
import org.prgrms.kdt.member.service.dto.MemberResponses;
import org.prgrms.kdt.member.service.mapper.ServiceMemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ServiceMemberMapper mapper;

    public MemberService(MemberRepository memberRepository, ServiceMemberMapper mapper) {
        this.memberRepository = memberRepository;
        this.mapper = mapper;
    }

    public MemberResponse createMember(CreateMemberServiceRequest request) {
        Member member = mapper.serviceRequestToMember(request);
        return new MemberResponse(memberRepository.insert(member));
    }

    public MemberResponses findAllBlackMember() {
        return MemberResponses.of(memberRepository.findByStatus(MemberStatus.BLACK));
    }

    public MemberResponses findAllMember() {
        return MemberResponses.of(memberRepository.findAll());
    }
}
