package org.programmers.springorder.controller;

import org.programmers.springorder.dto.voucher.VoucherRequestDto;
import org.programmers.springorder.dto.voucher.VoucherResponseDto;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherViewController {

    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String getVouchers(Model model) {
        List<VoucherResponseDto> vouchers = voucherService.getAllVoucher();
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @GetMapping("/{id}")
    public String getVoucher(@PathVariable UUID id, Model model) {
        Voucher voucher = voucherService.findById(id);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher";
    }

    @GetMapping("/new-voucher")
    public String newVoucher() {
        return "voucher/addForm";
    }

    @PostMapping("/new-voucher")
    public String newVoucher(@ModelAttribute VoucherRequestDto request) {   // TODO: discountValue Validation 필요
        voucherService.createVoucher(request);
        return "redirect:/vouchers";
    }

    @PostMapping("/{id}")
    public String deleteVoucher(@PathVariable UUID id) {
        voucherService.deleteVoucher(id);
        return "redirect:/vouchers";
    }
}
