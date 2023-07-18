package org.prgrms.kdt.member.controller;

import org.prgrms.kdt.member.dto.CreateMemberRequest;
import org.prgrms.kdt.member.dto.MemberResponses;
import org.prgrms.kdt.member.service.MemberService;
import org.springframework.stereotype.Component;

@Component
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public void createMember(CreateMemberRequest request) {
        memberService.createMember(request);
    }

    public MemberResponses findAllMember() {
        return memberService.findAllMember();
    }

    public MemberResponses findAllBlackMember() {
        return memberService.findAllBlackMember();
    }
}