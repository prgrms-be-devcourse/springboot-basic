package com.weeklyMission.voucher.controller;

import com.weeklyMission.client.VoucherType;
import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.voucher.dto.VoucherRequest;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.service.VoucherService;
import com.weeklyMission.wallet.service.WalletService;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vouchers")
public class VoucherViewController {

    private final WalletService walletService;

    private final VoucherService voucherService;

    public VoucherViewController(WalletService walletService, VoucherService voucherService) {
        this.walletService = walletService;
        this.voucherService = voucherService;
    }

    @ModelAttribute("voucherTypeList")
    public List<String> voucherType(){
        List<String> voucherTypeList = Arrays.stream(VoucherType.values())
            .map(v -> v.getType())
            .toList();
        return voucherTypeList;
    }

    @GetMapping("/createForm")
    public String createForm(Model model){
        model.addAttribute("voucher", new VoucherRequest());
        return "/vouchers/createForm";
    }

    @PostMapping
    public String create(@Validated @ModelAttribute("voucher") VoucherRequest voucher, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "vouchers/createForm";
        }

        try{
            voucherService.save(voucher);
        } catch(IllegalArgumentException e){
            bindingResult.addError(new FieldError("voucher", "amount", voucher.getAmount(), false, null,null, e.getMessage()));
            return "vouchers/createForm";
        }

        return "redirect:/vouchers";
    }

    @GetMapping
    public String findAll(Model model){
        List<VoucherResponse> voucherList = voucherService.findAll();
        model.addAttribute("voucherList", voucherList);
        return "/vouchers/voucherList";
    }

    @GetMapping("/{voucherId}")
    public String findById(@PathVariable("voucherId") String id, Model model){
        VoucherResponse voucher = voucherService.findById(id);
        model.addAttribute("voucher", voucher);
        return "vouchers/voucherInfo";
    }

    @GetMapping("/{voucherId}/members")
    public String findJoinMember(@PathVariable("voucherId") String voucherId, Model model){
        List<MemberResponse> memberList = walletService.findByVoucher(voucherId);
        model.addAttribute("memberList", memberList);
        return "/vouchers/vouchers_memberList";
    }

    @PostMapping("/{voucherId}")
    public String deleteById(@PathVariable("voucherId") String id){
        voucherService.deleteById(id);
        return "redirect:/vouchers";
    }
}
