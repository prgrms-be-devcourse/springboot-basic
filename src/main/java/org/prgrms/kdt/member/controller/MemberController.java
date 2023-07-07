package org.prgrms.kdt.member.controller;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.dto.CreateRequest;
import org.prgrms.kdt.member.service.MemberService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public void createMember(CreateRequest request){
        memberService.createMember(request);
    }

    public List<Member> findAllBlackMember() {
        return memberService.findAllBlackMember();
    }
}