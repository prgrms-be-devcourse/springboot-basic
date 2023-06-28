package org.programmers.VoucherManagement.member.api;

import org.programmers.VoucherManagement.member.application.MemberService;
import org.programmers.VoucherManagement.member.dto.GetMemberResponse;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public List<GetMemberResponse> getBlackMemberList(){
        return memberService.getBlackMemberList();
    }
}

