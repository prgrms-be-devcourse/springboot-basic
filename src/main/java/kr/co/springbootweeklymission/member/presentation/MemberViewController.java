package kr.co.springbootweeklymission.member.presentation;

import kr.co.springbootweeklymission.member.application.MemberService;
import kr.co.springbootweeklymission.member.presentation.dto.request.MemberReqDTO;
import kr.co.springbootweeklymission.member.presentation.dto.response.MemberResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/v1/members")
public class MemberViewController {
    private final MemberService memberService;

    @GetMapping("/post-page")
    public String createPage() {
        return "member/createMember";
    }

    @GetMapping("/put-page/{member_id}")
    public String updatePage(Model model,
                             @PathVariable(name = "member_id") UUID memberId) {
        model.addAttribute("memberId", memberId);
        return "member/updateMemberById";
    }

    @PostMapping
    public String createMember(@ModelAttribute @Validated MemberReqDTO.CREATE create) {
        memberService.createMember(create);
        return "redirect:/view/v1/members";
    }

    @PostMapping("/put/{member_id}")
    public String updateMemberById(@PathVariable(name = "member_id") UUID memberId,
                                   @ModelAttribute @Validated MemberReqDTO.UPDATE update) {
        memberService.updateMemberById(memberId, update);
        return "redirect:/view/v1/members/" + memberId;
    }

    @PostMapping("/delete/{member_id}")
    public String deleteMemberById(@PathVariable(name = "member_id") UUID memberId) {
        memberService.deleteMemberById(memberId);
        return "redirect:/view/v1/members";
    }

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
