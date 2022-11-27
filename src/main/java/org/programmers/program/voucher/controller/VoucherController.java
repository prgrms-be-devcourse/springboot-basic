package org.programmers.program.voucher.controller;


import org.programmers.program.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String voucherMainPage(Model model){
        var allVouchers = voucherService.getAllVouchers();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("vouchers", allVouchers);
        return "voucher-list";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String voucherDetailPage(@PathVariable("voucherId") UUID id, Model model){
        var voucher = voucherService.findById(id).get();
        model.addAttribute("voucher", voucher);
        return "voucher-detail";
    }

    @GetMapping("/vouchers/new")
    public String createVoucherPage(){
        return "newVouchers";
    }

    @PostMapping("/vouchers")
    public String addNewVouchers(CreateVoucherRequest request){
        voucherService.createVoucher(
                request.voucherType(),
                UUID.randomUUID(),
                request.discountAmount()
        );
        return "redirect:/vouchers";
    }
}
