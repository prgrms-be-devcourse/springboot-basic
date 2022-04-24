package com.prgms.management.voucher.controller;

import com.prgms.management.voucher.dto.VoucherRequest;
import com.prgms.management.voucher.dto.VoucherResponse;
import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("vouchers")
public class ViewVoucherController {
    private final VoucherService voucherService;
    private final String MENU_TYPE = "VOUCHER";
    
    public ViewVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    
    @GetMapping
    public String voucherList(Model model) {
        List<Voucher> voucherList = voucherService.findVouchers();
        List<VoucherResponse> list = voucherList.stream().map(VoucherResponse::of).toList();
        model.addAttribute("voucherList", list);
        model.addAttribute("menuType", MENU_TYPE);
        return "voucher/voucher_list";
    }
    
    @PostMapping
    public String voucherAdd(VoucherRequest request) {
        Voucher voucher = voucherService.addVoucher(request.toVoucher());
        return "redirect:/vouchers/" + voucher.getId().toString();
    }
    
    @GetMapping("new")
    public String voucherAddPage(Model model) {
        model.addAttribute("menuType", MENU_TYPE);
        return "voucher/voucher_add";
    }
    
    @GetMapping("{id}")
    public String voucherDetail(@PathVariable("id") UUID id, Model model) {
        Voucher voucher = voucherService.findVoucherById(id);
        model.addAttribute("voucher", VoucherResponse.of(voucher));
        model.addAttribute("menuType", MENU_TYPE);
        return "voucher/voucher_detail";
    }
    
    @DeleteMapping("{id}")
    public String voucherRemove(@PathVariable("id") UUID id) {
        voucherService.removeVoucherById(id);
        return "redirect:/vouchers";
    }
}
