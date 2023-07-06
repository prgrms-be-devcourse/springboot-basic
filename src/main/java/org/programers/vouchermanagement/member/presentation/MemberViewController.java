package org.programers.vouchermanagement.member.presentation;

import lombok.RequiredArgsConstructor;
import org.programers.vouchermanagement.member.application.MemberService;
import org.programers.vouchermanagement.member.dto.request.MemberCreationRequest;
import org.programers.vouchermanagement.member.dto.response.MemberResponse;
import org.programers.vouchermanagement.member.dto.response.MembersResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@RequestMapping("/view/members")
@RequiredArgsConstructor
@Controller
public class MemberViewController {

    private final MemberService memberService;

    @GetMapping
    public String save(Model model) {
        model.addAttribute("member", new MemberResponse());
        return "members/saveMember";
    }

    @PostMapping
    public String save(@Validated @ModelAttribute("member") MemberCreationRequest member,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "members/saveMember";
        }

        MemberResponse response = memberService.save(member);
        redirectAttributes.addAttribute("id", response.getId());
        return "redirect:/view/members/{id}";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable UUID id, Model model) {
        MemberResponse response = memberService.findById(id);
        model.addAttribute("member", response);
        return "members/member";
    }

    @GetMapping("/all")
    public String findAll(Model model) {
        MembersResponse response = memberService.findAll();
        model.addAttribute("members", response);
        return "members/members";
    }

    @PostMapping("/{id}")
    public String deleteById(@PathVariable UUID id) {
        memberService.deleteById(id);
        return "redirect:/view/members/all";
    }
}
