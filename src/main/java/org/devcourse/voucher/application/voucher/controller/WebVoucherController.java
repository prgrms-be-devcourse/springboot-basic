package org.devcourse.voucher.application.voucher.controller;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.controller.dto.VoucherResponse;
import org.devcourse.voucher.application.voucher.service.VoucherService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher")
public class WebVoucherController {

    private final VoucherService voucherService;

    public WebVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public String voucherMainPage(Model model, Pageable pageable) {
        List<VoucherResponse> vouchers = voucherService.recallAllVoucher(pageable);
        model.addAttribute("vouchers", vouchers);
        return "voucher/index";
    }

    @GetMapping("/new")
    public String voucherCreatePage() {
        return "voucher/new";
    }

    @GetMapping("/{id}")
    public String voucherUpdatePage(Model model, @PathVariable String id) {
        VoucherResponse voucher = voucherService.recallVoucherById(UUID.fromString(id));
        model.addAttribute("voucher", voucher);
        return "voucher/update";
    }

    @PostMapping("/new")
    public String postCreateVoucher(VoucherRequest voucherRequest) {
        voucherService.createVoucher(voucherRequest.getVoucherType(), voucherRequest.getDiscount());
        return "redirect:";
    }

    @PutMapping("/{id}")
    public String putUpdateVoucher(@PathVariable String id, VoucherRequest voucherRequest) {
        voucherService.updateVoucher(UUID.fromString(id), voucherRequest.getDiscount());
        return "redirect:";
    }

    @DeleteMapping("/{id}")
    public String deleteRemoveVoucher(@PathVariable String id) {
        voucherService.deleteVoucher(UUID.fromString(id));
        return "redirect:";
    }

}
