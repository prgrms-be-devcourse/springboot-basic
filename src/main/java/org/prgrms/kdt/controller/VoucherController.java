package org.prgrms.kdt.controller;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String showVoucherList(Model model) {
        VoucherMap voucherMap = voucherService.getVoucherList();
        Map<UUID, Voucher> vouchers = voucherMap.getVouchers();
        model.addAttribute("vouchers", vouchers);
        System.out.println("왔다");
        return "vouchers/voucherList";
    }
}
