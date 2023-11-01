package com.weeklyMission.member.controller;

import com.weeklyMission.member.dto.MemberRequest;
import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.member.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberViewController {

    private final MemberService memberService;

    public MemberViewController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/createForm")
    public String createForm(Model model){
        model.addAttribute("member", new MemberRequest(null, null, 0));
        return "/members/createForm";
    }

    @PostMapping
    public String create(@ModelAttribute MemberRequest member){
        memberService.save(member);
        return "redirect:/members";
    }

    @GetMapping
    public String findAll(Model model){
        List<MemberResponse> memberList = memberService.findAll();
        model.addAttribute("memberList", memberList);
        return "/members/memberList";
    }

    @GetMapping("/findForm")
    public String findByIdForm(){
        return "/members/findForm";
    }

    @GetMapping("/{memberId}")
    public String findById(@PathVariable("memberId") String id, Model model){
        MemberResponse member = memberService.findById(id);
        model.addAttribute("member", member);
        return "/members/memberInfo";
    }

    @PostMapping("/{memberId}")
    public String deleteById(@PathVariable("memberId") String id){
        memberService.deleteById(id);
        return "redirect:/members";
    }
}
