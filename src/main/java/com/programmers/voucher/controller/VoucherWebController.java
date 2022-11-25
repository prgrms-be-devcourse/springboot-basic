package com.programmers.voucher.controller;

import com.programmers.voucher.controller.dto.VoucherCreateRequest;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
