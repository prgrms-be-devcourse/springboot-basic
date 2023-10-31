package com.programmers.vouchermanagement.controller;

import com.programmers.vouchermanagement.dto.VoucherDto;
import com.programmers.vouchermanagement.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public String showAllVouchers(Model model) {
        final List<VoucherDto.Response> vouchers = voucherService.findAllVouchers().stream()
                .map(VoucherDto.Response::new).toList();
        model.addAttribute("vouchers", vouchers);
        return "/voucher/voucher_detail";
    }

    @GetMapping("/add")
    public String showAddVoucherForm() {
        return "/voucher/add_voucher_form";
    }

    @PostMapping("/add")
    public String createVoucher(@ModelAttribute VoucherDto.CreateRequest voucherDto) {
        try {
            voucherService.createVoucher(voucherDto);
        } catch (Exception e) {
            return "redirect:/voucher/add?error=" + e.getMessage();
        }
        return "redirect:/voucher";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVoucher(@RequestParam("id") String id) {
        voucherService.deleteVoucher(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }
}
