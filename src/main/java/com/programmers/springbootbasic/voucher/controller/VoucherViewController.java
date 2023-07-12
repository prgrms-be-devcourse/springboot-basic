package com.programmers.springbootbasic.voucher.controller;

import com.programmers.springbootbasic.voucher.dto.VouchersResponseDto;
import com.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("voucher")
@Controller
public class VoucherViewController {

    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        VouchersResponseDto vouchersResponseDto = voucherService.findAll();
        model.addAttribute("vouchers", vouchersResponseDto);

        return "voucher/voucher-list";
    }
}