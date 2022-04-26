package com.prgrms.management.voucher.controller;

import com.prgrms.management.voucher.domain.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MvcController {
    private final VoucherService voucherService;

    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
    }

    @GetMapping
    public String todayCreatedVouchersInfo(Model model) {
        model.addAttribute("vouchers", voucherService.findAllByVoucherTypeOrCreatedAt(null, LocalDate.now()));
        return "index";
    }

    @GetMapping("/voucher/info")
    public String voucherInfo(Model model) {
        model.addAttribute("vouchers", voucherService.findAll());
        return "voucher/info";
    }

    @GetMapping("/voucher/create")
    public String createVoucherForm(Model model) {
        model.addAttribute("voucherRequest", new VoucherRequest());
        return "voucher/create";
    }

    @GetMapping("/voucher/search")
    public String searchVoucher() {
        return "voucher/search";
    }

    @PostMapping("/voucher/createVoucher")
    public String createVoucher(VoucherRequest voucherRequest) {
        voucherService.createVoucher(voucherRequest);
        return "redirect:/voucher/info";
    }

    @PostMapping("/voucher/search")
    public String searchVoucher(
            Model model,
            @RequestParam(value = "voucherType", required = false) VoucherType voucherType,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "createdAt", required = false) LocalDate createdAt
    ) {
        model.addAttribute("msg","HELLO");
        model.addAttribute("vouchers", voucherService.findAllByVoucherTypeOrCreatedAt(voucherType, createdAt));
        return "voucher/searchResult";
    }

    @DeleteMapping("/voucher/{voucherId}")
    public String deleteVoucher(@PathVariable String voucherId) {
        voucherService.deleteById(UUID.fromString(voucherId));
        return "redirect:/voucher/info";
    }
}
