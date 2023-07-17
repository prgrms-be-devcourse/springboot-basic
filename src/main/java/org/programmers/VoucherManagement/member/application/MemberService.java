package org.programmers.VoucherManagement.member.application;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.dto.CreateMemberRequest;
import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.member.dto.UpdateMemberRequest;
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

    public GetMemberListResponse getAllMembers() {
        return new GetMemberListResponse(repository.findAll());
    }

    public GetMemberListResponse getAllBlackMembers() {
        return new GetMemberListResponse(repository.findAllByMemberStatus(MemberStatus.BLACK));
    }

    @Transactional
    public void createMember(CreateMemberRequest createMemberRequest) {
        Member member = new Member(UUID.randomUUID(),
                createMemberRequest.getName(),
                createMemberRequest.getMemberStatus());
        repository.insert(member);
    }

    @Transactional
    public void updateMember(UUID memberId, UpdateMemberRequest updateMemberRequest) {
        Member member = repository.findById(memberId).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
        member.changeMemberStatus(updateMemberRequest.getMemberStatus());
        repository.update(member);
    }

    @Transactional
    public void deleteMember(UUID memberId) {
        repository.delete(memberId);
    }
}
