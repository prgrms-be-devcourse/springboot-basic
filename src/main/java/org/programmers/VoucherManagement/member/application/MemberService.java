package org.programmers.VoucherManagement.member.application;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.dto.request.MemberCreateRequest;
import org.programmers.VoucherManagement.member.dto.request.MemberUpdateRequest;
import org.programmers.VoucherManagement.member.dto.response.MemberGetResponses;
import org.programmers.VoucherManagement.member.exception.MemberException;
import org.programmers.VoucherManagement.member.infrastructure.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.programmers.VoucherManagement.member.exception.MemberExceptionMessage.NOT_FOUND_MEMBER;

@Component
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository memberRepository) {
        this.repository = memberRepository;
    }

    public MemberGetResponses getAllMembers() {
        return new MemberGetResponses(repository.findAll());
    }

    public MemberGetResponses getAllBlackMembers() {
        return new MemberGetResponses(repository.findAllByMemberStatus(MemberStatus.BLACK));
    }

    @Transactional
    public void createMember(MemberCreateRequest memberCreateRequest) {
        Member member = new Member(UUID.randomUUID(),
                memberCreateRequest.name(),
                memberCreateRequest.memberStatus());
        repository.insert(member);
    }

    @Transactional
    public void updateMember(UUID memberId, MemberUpdateRequest memberUpdateRequest) {
        Member member = repository.findById(memberId).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
        member.changeMemberStatus(memberUpdateRequest.memberStatus());
        repository.update(member);
    }

    @Transactional
    public void deleteMember(UUID memberId) {
        repository.delete(memberId);
    }
}
