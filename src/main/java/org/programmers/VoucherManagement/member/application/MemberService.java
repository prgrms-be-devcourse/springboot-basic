package org.programmers.VoucherManagement.member.application;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.dto.CreateMemberRequest;
import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.member.dto.UpdateMemberRequest;
import org.programmers.VoucherManagement.member.exception.MemberException;
import org.programmers.VoucherManagement.member.infrastructure.MemberRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.programmers.VoucherManagement.member.exception.MemberExceptionMessage.NOT_FOUND_MEMBER;

@Component
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    public GetMemberListResponse getAllMembers() {
        return new GetMemberListResponse(repository.findAll());
    }

    public GetMemberListResponse getAllBlackMembers() {
        return new GetMemberListResponse(repository.findAllByMemberStatus());
    }

    public void createMember(CreateMemberRequest createMemberRequest) {
        Member member = new Member(UUID.randomUUID(),
                createMemberRequest.getName(),
                createMemberRequest.getMemberStatus());
        repository.insert(member);
    }

    public void updateMember(UUID memberId, UpdateMemberRequest updateMemberRequest) {
        Member member = repository.findById(memberId).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
        member.changeMemberStatus(updateMemberRequest.getMemberStatus());
        repository.update(member);
    }

    public void deleteMember(UUID memberId) {
        Member member = repository.findById(memberId).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
        repository.delete(member);
    }
}
