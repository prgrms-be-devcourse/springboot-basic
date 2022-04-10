package com.programmers.part1.member;

import com.programmers.part1.member.entity.MemberDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class MemberController {

    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public List<MemberDto> list() throws IOException {
        // TODO : 블랙 멤버 없을 경우 에러처리
        return memberService.getAllBlackMembers();
    }
}
