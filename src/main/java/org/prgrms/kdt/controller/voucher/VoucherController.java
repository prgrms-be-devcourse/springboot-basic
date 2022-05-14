package org.prgrms.kdt.controller.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String voucherPage(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers.stream()
            .map(VoucherDto::new)
            .collect(Collectors.toList()));

        return "voucher-list";
    }
}
