package org.programmers.VoucherManagement.member.presentation;

import org.programmers.VoucherManagement.member.application.MemberService;
import org.programmers.VoucherManagement.member.application.dto.MemberCreateRequest;
import org.programmers.VoucherManagement.member.application.dto.MemberGetResponses;
import org.programmers.VoucherManagement.member.application.dto.MemberUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public MemberGetResponses getAllMembers() {
        return memberService.getAllMembers();
    }

    public MemberGetResponses getAllBlackMembers() {
        return memberService.getAllBlackMembers();
    }

    public void createMember(MemberCreateRequest memberCreateRequest) {
        memberService.createMember(memberCreateRequest);
    }

    public void updateMember(String memberId, MemberUpdateRequest memberUpdateRequest) {
        memberService.updateMember(memberId, memberUpdateRequest);
    }

    public void deleteMember(String memberId) {
        memberService.deleteMember(memberId);
    }
}
