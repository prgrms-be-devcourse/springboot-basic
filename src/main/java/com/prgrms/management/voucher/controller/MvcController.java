package com.prgrms.management.voucher.controller;

import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MvcController {
    private final VoucherService voucherService;

    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
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

    @PostMapping("/voucher/createVoucher")
    public String createVoucher(VoucherRequest voucherRequest) {
        System.out.println("voucherRequest.getVoucherType() = " + voucherRequest.getAmount());
        voucherService.createVoucher(voucherRequest);
        return "redirect:/voucher/info";
    }

    @DeleteMapping("/voucher/{voucherId}")
    public String deleteVoucher(@PathVariable String voucherId) {
        voucherService.deleteById(UUID.fromString(voucherId));
        return "redirect:/voucher/info";
    }
}
