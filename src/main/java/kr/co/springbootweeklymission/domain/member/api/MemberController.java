package kr.co.springbootweeklymission.domain.member.api;

import kr.co.springbootweeklymission.domain.member.application.MemberService;
import kr.co.springbootweeklymission.domain.member.dto.response.MemberResDTO;
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
