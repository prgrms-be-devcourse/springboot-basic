package com.pppp0722.vouchermanagement.controller.view.member;

import com.pppp0722.vouchermanagement.controller.dto.CreateMemberRequest;
import com.pppp0722.vouchermanagement.controller.dto.DeleteMemberRequest;
import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.member.service.MemberService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(
        MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member")
    public String findAllPage(Model model) {
        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "member/list";
    }

    @GetMapping("/member/{memberId}")
    public String detailPage(@PathVariable("memberId") UUID memberId, Model model) {
        Member member = memberService.getMemberById(memberId);
        model.addAttribute("member", member);
        return "member/detail";
    }

    @GetMapping("/member/create")
    public String createPage() {
        return "member/create";
    }

    @PostMapping("/member/create")
    public String createPage(CreateMemberRequest createMemberRequest) {
        memberService.createMember(UUID.randomUUID(), createMemberRequest.name());
        return "redirect:/member/create";
    }

    @GetMapping("/member/delete")
    public String deletePage() {
        return "member/delete";
    }

    @PostMapping("/member/delete")
    public String deletePage(DeleteMemberRequest deleteMemberRequest) {
        memberService.deleteMember(deleteMemberRequest.memberId());
        return "redirect:/member/delete";
    }
}
