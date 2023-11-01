package com.weeklyMission.voucher.controller;

import com.weeklyMission.client.VoucherType;
import com.weeklyMission.voucher.dto.VoucherRequest;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.service.VoucherService;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vouchers")
public class VoucherViewController {

    private final VoucherService voucherService;

    @ModelAttribute("voucherTypeList")
    public List<String> voucherType(){
        List<String> voucherTypeList = Arrays.stream(VoucherType.values())
            .map(v -> v.getType())
            .toList();
        return voucherTypeList;
    }

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/createForm")
    public String createForm(Model model){
        model.addAttribute("voucher", new VoucherRequest(null, 0));
        return "/vouchers/createForm";
    }

    @PostMapping
    public String create(@ModelAttribute VoucherRequest voucher){
        voucherService.save(voucher);
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

    @PostMapping("/{voucherId}")
    public String deleteById(@PathVariable("voucherId") String id){
        voucherService.deleteById(id);
        return "redirect:/vouchers";
    }
}
