package org.prgrms.kdt.member.controller;

import org.prgrms.kdt.member.dto.CreateMemberRequest;
import org.prgrms.kdt.member.dto.MembersResponse;
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

    public MembersResponse findAllMember() {
        return memberService.findAllMember();
    }

    public MembersResponse findAllBlackMember() {
        return memberService.findAllBlackMember();
    }
}