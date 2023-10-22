package com.weeklyMission.member.controller;

import com.weeklyMission.member.domain.Member;
import com.weeklyMission.member.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public List<Member> getBlackList(){
        return memberService.getBlackList();
    }
}
