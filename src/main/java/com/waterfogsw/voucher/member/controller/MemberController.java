package com.waterfogsw.voucher.member.controller;

import com.waterfogsw.voucher.member.service.MemberService;

import java.util.List;

public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    List<String> getBlackList() {
        return null;
    }
}
