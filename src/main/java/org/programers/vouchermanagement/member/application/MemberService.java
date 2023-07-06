package org.programers.vouchermanagement.member.application;

import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.member.domain.BlacklistRepository;
import org.programers.vouchermanagement.member.domain.MemberRepository;
import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.member.dto.request.MemberCreationRequest;
import org.programers.vouchermanagement.member.dto.response.MemberResponse;
import org.programers.vouchermanagement.member.dto.request.MemberUpdateRequest;
import org.programers.vouchermanagement.member.dto.response.MembersResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final BlacklistRepository blacklistRepository;
    private final MemberRepository memberRepository;

    public MemberService(BlacklistRepository blacklistRepository, MemberRepository memberRepository) {
        this.blacklistRepository = blacklistRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse save(MemberCreationRequest request) {
        Member savingMember = new Member(request.getStatus());
        Member member = memberRepository.save(savingMember);
        return new MemberResponse(member);
    }

    public MemberResponse findById(UUID id) {
        Member member = memberRepository.getById(id);
        return new MemberResponse(member);
    }

    public MembersResponse findAll() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> responses = members.stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
        return new MembersResponse(responses);
    }

    public MembersResponse findAllInBlacklist(MemberStatus status) {
        List<Member> members = blacklistRepository.findAllByStatus(status);
        List<MemberResponse> responses = members.stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
        return new MembersResponse(responses);
    }

    @Transactional
    public void update(MemberUpdateRequest request) {
        Member member = new Member(request.getId(), request.getStatus());
        memberRepository.update(member);
    }

    @Transactional
    public void deleteById(UUID id) {
        memberRepository.deleteById(id);
    }
}
