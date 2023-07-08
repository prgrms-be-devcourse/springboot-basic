package kr.co.springbootweeklymission.member.presentation;

import kr.co.springbootweeklymission.member.application.MemberService;
import kr.co.springbootweeklymission.member.presentation.dto.response.MemberResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/v1/members")
public class MemberViewController {
    private final MemberService memberService;

    @GetMapping
    public String getMembersAll(Model model) {
        List<MemberResDTO.READ> reads = memberService.getMembersAll();
        model.addAttribute("members", reads);
        return "member/getMembersAll";
    }

    @GetMapping("/{member_id}")
    public String getMemberById(Model model,
                                @PathVariable(name = "member_id") UUID memberId) {
        MemberResDTO.READ read = memberService.getMemberById(memberId);
        model.addAttribute("member", read);
        return "member/getMemberById";
    }
}
