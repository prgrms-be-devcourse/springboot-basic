package org.programers.vouchermanagement.member.presentation;

import org.programers.vouchermanagement.member.application.MemberService;
import org.programers.vouchermanagement.member.dto.MembersResponse;
import org.programers.vouchermanagement.view.OutputView;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public void findAllByBlackStatus() {
        MembersResponse response = memberService.findAllByBlackStatus();
        OutputView.outputMembers(response);
    }
}
