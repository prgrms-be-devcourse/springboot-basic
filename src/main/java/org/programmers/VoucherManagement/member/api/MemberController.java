package org.programmers.VoucherManagement.member.api;

import org.programmers.VoucherManagement.member.application.MemberService;
import org.programmers.VoucherManagement.member.dto.GetMemberListRes;
import org.programmers.VoucherManagement.member.dto.GetMemberRes;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public GetMemberListRes getBlackMemberList(){
        return memberService.getBlackMemberList();
    }
}

