package com.programmers.voucher.controller.voucher;

import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
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
@RequestMapping("/")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping("voucher")
    public String create() {
        return "voucher/voucher_new";
    }

    @PostMapping("voucher")
    public String create(VoucherCreateRequest voucherCreateRequest) {
        voucherService.create(voucherCreateRequest);
        return "redirect:/";
    }

    @GetMapping("vouchers")
    public String findAll(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher_list";
    }

    @GetMapping("voucher/{voucherId}")
    public String findById(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher_detail";
    }

    @GetMapping("vouchers/{email}")
    public String findAllByCustomer(@PathVariable String email, Model model) {
        List<Voucher> vouchers = voucherService.findAllByCustomer(email);
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher_list";
    }
}
