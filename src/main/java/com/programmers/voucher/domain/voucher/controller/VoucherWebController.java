package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.dto.request.VoucherSearchRequest;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherWebController {
    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String findVouchers(Model model,
                               @ModelAttribute VoucherSearchRequest request) {
        List<VoucherDto> vouchers = voucherService.findVouchers(
                request.getVoucherType(),
                request.getStartTime(), request.getEndTime());
        model.addAttribute("vouchers", vouchers);
        return "vouchers/voucher-list";
    }

    @GetMapping("/vouchers/new")
    public String createVoucher() {
        return "vouchers/voucher-create";
    }

    @PostMapping("/vouchers/new")
    public String createVoucher(@ModelAttribute VoucherCreateRequest request) {
        UUID voucherId = voucherService.createVoucher(request.getVoucherType(), request.getAmount());
        return "redirect:/vouchers/" + voucherId;
    }

    @GetMapping("/vouchers/{voucherId}")
    public String findVoucher(Model model,
                              @PathVariable UUID voucherId) {
        VoucherDto voucher = voucherService.findVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "vouchers/voucher";
    }

    @PostMapping("/vouchers/{voucherId}/delete")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }
}
