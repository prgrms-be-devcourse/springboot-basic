package org.programmers.VoucherManagement.member.presentation;

import org.programmers.VoucherManagement.member.application.MemberService;
import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.springframework.stereotype.Component;

@Component
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public GetMemberListResponse getBlackMemberList() {
        return memberService.getBlackMemberList();
    }
}

