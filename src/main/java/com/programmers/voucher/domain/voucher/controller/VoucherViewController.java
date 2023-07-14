package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.dto.VoucherResponse;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vouchers")
public class VoucherViewController {
    private final VoucherService voucherService;

    @PostMapping
    public String createVoucher(VoucherCreateRequest request) {
        voucherService.createVoucher(request);
        return "redirect:/vouchers";
    }

    @GetMapping
    public String getAllVouchers(Model model) {
        List<VoucherResponse> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);

        return "vouchers/index";
    }

    @GetMapping("/{voucherId}")
    public String getVoucher(@PathVariable UUID voucherId, Model model) {
        VoucherResponse voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        
        return "vouchers/detail";
    }

    @PostMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }
}
