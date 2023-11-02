package com.weeklyMission.member.controller;

import com.weeklyMission.member.dto.MemberRequest;
import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.member.service.MemberService;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.service.VoucherService;
import com.weeklyMission.wallet.service.WalletService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/members")
public class MemberViewController {

    private final WalletService walletService;
    private final MemberService memberService;
    private final VoucherService voucherService;

    public MemberViewController(WalletService walletService, MemberService memberService,
        VoucherService voucherService) {
        this.walletService = walletService;
        this.memberService = memberService;
        this.voucherService = voucherService;
    }

    @GetMapping("/createForm")
    public String createForm(Model model){
        model.addAttribute("member", new MemberRequest());
        return "/members/createForm";
    }

    @PostMapping
    public String create(@Validated @ModelAttribute("member") MemberRequest member, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/members/createForm";
        }
        memberService.save(member);
        return "redirect:/members";
    }

    @GetMapping
    public String findAll(Model model){
        List<MemberResponse> memberList = memberService.findAll();
        model.addAttribute("memberList", memberList);
        return "/members/memberList";
    }

    @GetMapping("/{memberId}")
    public String findById(@PathVariable("memberId") String id, Model model){
        MemberResponse member = memberService.findById(id);
        model.addAttribute("member", member);
        return "/members/memberInfo";
    }

    @GetMapping("/{memberId}/vouchers")
    public String findGetVouchers(@PathVariable("memberId") String memberId, @RequestParam String name, Model model){
        List<VoucherResponse> voucherList = walletService.findByMember(memberId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("name", name);
        model.addAttribute("voucherList", voucherList);
        return "/members/members_voucherList";
    }

    @GetMapping("{memberId}/vouchers/{voucherId}")
    public String findGetVoucherDetail(@PathVariable("memberId") String memberId, @PathVariable("voucherId") String voucherId, Model model){
        VoucherResponse voucher = voucherService.findById(voucherId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("voucher", voucher);
        return "/members/members_voucherDetail";
    }

    @PostMapping("/{memberId}")
    public String deleteById(@PathVariable("memberId") String id){
        memberService.deleteById(id);
        return "redirect:/members";
    }
}
