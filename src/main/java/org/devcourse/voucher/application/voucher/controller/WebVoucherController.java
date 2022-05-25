package org.devcourse.voucher.application.voucher.controller;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.devcourse.voucher.application.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/voucher")
public class WebVoucherController {

    private final VoucherService voucherService;

    public WebVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping("")
    public String voucherMainPage(Model model) {
        List<Voucher> vouchers = voucherService.recallAllVoucher();
        model.addAttribute("vouchers", vouchers);
        return "voucher/index";
    }

    @GetMapping("/new")
    public String voucherCreatePage() {
        return "voucher/new";
    }

    @GetMapping("/{id}")
    public String voucherUpdatePage() {
        return "update/new";
    }

    @PostMapping("/new")
    public String postCreateVoucher(VoucherRequest voucherRequest) {
        voucherService.createVoucher(voucherRequest.voucherType(), voucherRequest.price());
        return "redirect:";
    }

    @PutMapping("/{id}")
    public String putUpdateVoucher() {
        // TODO update logic
        return "redirect:";
    }

    @DeleteMapping("/{id}")
    public String deleteRemoveVoucher() {
        // TODO delete logic
        return "redirect:";
    }

}
