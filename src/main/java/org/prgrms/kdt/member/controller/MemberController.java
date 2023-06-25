package org.prgrms.kdt.member.controller;

import org.prgrms.kdt.commendLine.ConsoleOutput;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.service.MemberService;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public void findAllBlackMember() throws IOException {
        List<Member> allBlackMember = memberService.findAllBlackMember();
        ConsoleOutput.printAllBlackList(allBlackMember);
    }
}
