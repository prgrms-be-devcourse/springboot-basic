package kr.co.springbootweeklymission.member.api;

import kr.co.springbootweeklymission.member.api.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    public List<MemberResDTO.READ> getMembersByBlack() {
        return memberService.getMembersByBlack();
    }
}
