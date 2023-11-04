package com.programmers.vouchermanagement.voucher.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;

@Controller
@RequestMapping("/vouchers")
public class VoucherAdminController {
    private final VoucherService voucherService;

    public VoucherAdminController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String readAllVouchers(Model model) {
        List<VoucherResponse> vouchers = voucherService.readAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers/vouchers";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("voucher", new Voucher());
        return "vouchers/voucher";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute CreateVoucherRequest request, Model model) {
        VoucherResponse voucher = voucherService.create(request);
        model.addAttribute("voucher", voucher);
        return "redirect:/vouchers/" + voucher.voucherId();
    }

    @GetMapping("/{voucherId}")
    public String findById(@PathVariable UUID voucherId, Model model) {
        VoucherResponse voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "vouchers/voucher";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute UpdateVoucherRequest request, Model model) {
        VoucherResponse voucher = voucherService.update(request);
        model.addAttribute("voucher", voucher);
        return "redirect:/vouchers/" + voucher.voucherId();
    }

    @PostMapping("/{voucherId}/delete")
    public String deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return "vouchers/deleted";
    }
}
