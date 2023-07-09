package org.programmers.VoucherManagement.member.presentation;

import org.programmers.VoucherManagement.member.application.MemberService;
import org.programmers.VoucherManagement.member.dto.CreateMemberRequest;
import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.member.dto.UpdateMemberRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public GetMemberListResponse getAllMembers() {
        return memberService.getAllMembers();
    }

    public GetMemberListResponse getAllBlackMembers() {
        return memberService.getAllBlackMembers();
    }

    public void createMember(CreateMemberRequest createMemberRequest) {
        memberService.createMember(createMemberRequest);
    }

    public void updateMember(UUID memberId, UpdateMemberRequest updateMemberRequest) {
        memberService.updateMember(memberId, updateMemberRequest);
    }

    public void deleteMember(UUID memberId) {
        memberService.deleteMember(memberId);
    }
}

