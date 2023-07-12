package com.prgms.VoucherApp.domain.voucher.controller;

import com.prgms.VoucherApp.domain.voucher.dto.VoucherResponse;
import com.prgms.VoucherApp.domain.voucher.dto.VouchersResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String customer(@PathVariable String id, Model model) {
        UUID inputVoucherId = UUID.fromString(id);
        VoucherResponse voucherResponse = voucherService.findOne(inputVoucherId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id가 입력되었습니다."));
        model.addAttribute("voucher", voucherResponse);
        return "voucher/voucher";
    }
}
