package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.dto.VoucherResponse;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherViewController {
    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

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

    @PostMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }
}
