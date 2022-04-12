package com.programmers.part1.member;

import com.programmers.part1.error.member.BlackListEmptyException;
import com.programmers.part1.member.entity.Member;
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
        List<MemberDto> blacks = memberService.getAllBlackMembers();
        if(blacks.isEmpty())
            throw new BlackListEmptyException("블랙 회원이 없습니다.\n");
        return blacks;
    }
}
