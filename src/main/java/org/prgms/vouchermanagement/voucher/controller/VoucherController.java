package org.prgms.vouchermanagement.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.service.ThymeleafVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final ThymeleafVoucherService voucherService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Voucher> allVouchers = voucherService.getVouchers();
        model.addAttribute("allVouchers", allVouchers);
        return "vouchers";
    }

}
