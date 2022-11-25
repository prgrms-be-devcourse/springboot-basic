package com.programmers.voucher.controller;

import com.programmers.voucher.controller.dto.VoucherCreateRequest;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher/")
public class VoucherWebController {

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping(value = {"", "/"})
    public String main() {
        return "main";
    }

    @GetMapping("newvoucher")
    public String create() {
        return "voucher/newVoucher";
    }

    @PostMapping("newvoucher")
    public String create(VoucherCreateRequest voucherCreateRequest) {
        voucherService.create(voucherCreateRequest);
        return "redirect:/voucher/";
    }

    @GetMapping("voucherlist")
    public String findAll(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "voucher/listVoucher";
    }

    @GetMapping("onevoucher/{voucherId}")
    public String findById(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/oneVoucher";
    }
}
