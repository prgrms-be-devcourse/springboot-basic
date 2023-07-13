package com.prgms.VoucherApp.domain.voucher.controller;

import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateRequest;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResponse;
import com.prgms.VoucherApp.domain.voucher.dto.VouchersResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String vouchers(Model model) {
        VouchersResponse vouchersResponse = voucherService.findAll();
        List<VoucherResponse> vouchers = vouchersResponse.getVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @GetMapping("/{id}")
    public String voucher(@PathVariable String id, Model model) {
        UUID inputVoucherId = UUID.fromString(id);
        VoucherResponse voucherResponse = voucherService.findOne(inputVoucherId);
        model.addAttribute("voucher", voucherResponse);
        return "voucher/voucher";
    }

    @GetMapping("/add")
    public String addVoucherForm(Model model) {
        model.addAttribute("voucher", new VoucherCreateRequest());
        model.addAttribute("voucherTypes", VoucherType.values());
        return "voucher/add_voucher";
    }

    @PostMapping("/add")
    public String addVoucher(@ModelAttribute VoucherCreateRequest voucherCreateRequest) {
        voucherService.save(voucherCreateRequest);
        return "redirect:/vouchers";
    }
}
