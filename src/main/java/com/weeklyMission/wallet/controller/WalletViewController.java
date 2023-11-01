package com.weeklyMission.wallet.controller;

import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.member.service.MemberService;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.service.VoucherService;
import com.weeklyMission.wallet.dto.WalletRequest;
import com.weeklyMission.wallet.service.WalletService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wallet")
public class WalletViewController {

    private final WalletService walletService;
    private final VoucherService voucherService;
    private final MemberService memberService;

    public WalletViewController(WalletService walletService, VoucherService voucherService,
        MemberService memberService) {
        this.walletService = walletService;
        this.voucherService = voucherService;
        this.memberService = memberService;
    }

    @ModelAttribute("voucherList")
    public List<String> voucherList(){
        List<String> voucherList = voucherService.findAll()
            .stream()
            .map(VoucherResponse::voucherId)
            .toList();
        return voucherList;
    }

    @ModelAttribute("memberList")
    public List<MemberResponse> voucherMemberList(){
        List<MemberResponse> memberList = memberService.findAll();
        return memberList;
    }

    @GetMapping
    public String createForm(Model model){
        model.addAttribute("wallet", new WalletRequest(null, null));
        return "/wallet/createForm";
    }

    @PostMapping
    public String create(@ModelAttribute WalletRequest wallet){
        walletService.save(wallet);
        return "redirect:/";
    }
}
