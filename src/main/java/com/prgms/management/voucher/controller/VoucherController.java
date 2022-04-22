package com.prgms.management.voucher.controller;

import com.prgms.management.voucher.controller.dto.VoucherRequest;
import com.prgms.management.voucher.controller.dto.VoucherResponse;
import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;
    private final String MENU_TYPE = "VOUCHER";
    
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    
    @GetMapping("/vouchers")
    public String voucherList(Model model) {
        List<Voucher> voucherList = voucherService.findVouchers();
        List<VoucherResponse> list = voucherList.stream().map(VoucherResponse::of).toList();
        model.addAttribute("voucherList", list);
        model.addAttribute("menuType", MENU_TYPE);
        return "voucher_list";
    }
    
    @GetMapping("/vouchers/{id}")
    public String voucherDetail(@PathVariable("id") UUID id, Model model) {
        Voucher voucher = voucherService.findVoucherById(id);
        model.addAttribute("voucher", VoucherResponse.of(voucher));
        model.addAttribute("menuType", MENU_TYPE);
        return "voucher_detail";
    }
    
    @GetMapping("/voucher/new")
    public String voucherAddPage(Model model) {
        model.addAttribute("menuType", MENU_TYPE);
        return "voucher_add";
    }
    
    @PostMapping("/voucher")
    public String voucherAdd(VoucherRequest request) {
        Voucher voucher = voucherService.addVoucher(request.toVoucher());
        return "redirect:/vouchers/" + voucher.getId().toString();
    }
    
    @DeleteMapping("/vouchers/{id}")
    public String voucherRemove(@PathVariable("id") UUID id) {
        voucherService.removeVoucherById(id);
        return "redirect:/vouchers";
    }
}
