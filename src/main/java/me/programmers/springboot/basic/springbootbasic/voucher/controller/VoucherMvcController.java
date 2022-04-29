package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherMvcController {

    private final JdbcVoucherService voucherService;

    public VoucherMvcController(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String showVouchers(Model model) {
        List<Voucher> fixVouchers = voucherService.getAllFixVouchers();
        List<Voucher> percentVouchers = voucherService.getAllPercentVouchers();
        model.addAttribute("fixVouchers", fixVouchers);
        model.addAttribute("percentVouchers", percentVouchers);
        return "vouchers";
    }

    @GetMapping("/vouchers/{uuid}")
    public String showDetailVoucher(@PathVariable UUID uuid, Model model) {
        Voucher voucher = voucherService.getVoucherById(uuid);
        model.addAttribute("voucher", voucher);
        return "detail";
    }

}
