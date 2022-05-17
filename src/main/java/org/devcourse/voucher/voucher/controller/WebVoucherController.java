package org.devcourse.voucher.voucher.controller;

import org.devcourse.voucher.voucher.controller.dto.CreateVoucherRequest;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebVoucherController {

    private final VoucherService voucherService;

    public WebVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping("/voucher")
    public String voucherMainPage(Model model) {
        List<Voucher> vouchers = voucherService.recallAllVoucher();
        model.addAttribute("vouchers", vouchers);
        return "voucher/index";
    }

    @GetMapping("/voucher/new")
    public String voucherCreatePage() {
        return "voucher/new";
    }

    @PostMapping("/voucher/new")
    public String postCreateVoucher(CreateVoucherRequest createVoucherRequest) {
        voucherService.createVoucher(createVoucherRequest.getVoucherType(), createVoucherRequest.getPrice());
        return "redirect:/voucher";
    }
}
