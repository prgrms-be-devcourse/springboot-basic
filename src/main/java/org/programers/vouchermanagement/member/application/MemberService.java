package org.programers.vouchermanagement.member.application;

import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.member.domain.BlacklistRepository;
import org.programers.vouchermanagement.member.domain.MemberRepository;
import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.member.dto.MemberCreationRequest;
import org.programers.vouchermanagement.member.dto.MemberResponse;
import org.programers.vouchermanagement.member.dto.MemberUpdateRequest;
import org.programers.vouchermanagement.member.dto.MembersResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final BlacklistRepository blacklistRepository;
    private final MemberRepository memberRepository;

    public MemberService(BlacklistRepository blacklistRepository, MemberRepository memberRepository) {
        this.blacklistRepository = blacklistRepository;
        this.memberRepository = memberRepository;
    }

    public MemberResponse save(MemberCreationRequest request) {
        Member member = memberRepository.save(new Member(request.getStatus()));
        return new MemberResponse(member);
    }

    public MemberResponse findById(UUID id) {
        Member member = memberRepository.getById(id);
        return new MemberResponse(member);
    }

    public MembersResponse findAll() {
        List<Member> members = memberRepository.findAll();
        return new MembersResponse(members.stream().map(MemberResponse::new).collect(Collectors.toList()));
    }

    public MembersResponse findAllInBlacklist(MemberStatus status) {
        List<Member> members = blacklistRepository.findAllByStatus(status);
        return new MembersResponse(members.stream().map(MemberResponse::new).collect(Collectors.toList()));
    }

    public void update(MemberUpdateRequest request) {
        memberRepository.update(new Member(request.getId(), request.getStatus()));
    }

    public void deleteById(UUID id) {
        memberRepository.deleteById(id);
    }
}
