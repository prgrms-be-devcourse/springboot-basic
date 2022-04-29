package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

}
