package org.weekly.weekly.voucher.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;
import org.weekly.weekly.voucher.dto.response.VoucherCreationResponse;
import org.weekly.weekly.voucher.dto.response.VouchersResponse;
import org.weekly.weekly.voucher.exception.VoucherException;
import org.weekly.weekly.voucher.service.VoucherService;
import org.weekly.weekly.global.handler.WebExceptionDto;

@Controller
@RequestMapping("/voucher")
public class VoucherWebController {

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/menu")
    public String menu() {
        return "voucher/menu";
    }

    @PostMapping("/create")
    public String createVoucher(@ModelAttribute VoucherCreationRequest voucherCreationRequest, Model model) {
        try {
            VoucherCreationResponse voucherCreationResponse = voucherService.insertVoucher(voucherCreationRequest);
            model.addAttribute("voucher", voucherCreationResponse);
        } catch (VoucherException voucherException) {
            model.addAttribute("exception", new WebExceptionDto(voucherException));
            return "exception/exception";
        }
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createVoucher(Model model) {
        model.addAttribute("voucherCreationRequest", new VoucherCreationRequest(null, null));
        return "voucher/create";
    }

    @GetMapping("/vouchers")
    public String getVouchers(Model model) {
        try {
            VouchersResponse vouchersResponse = voucherService.getVouchers();
            model.addAttribute("vouchers", vouchersResponse);
        } catch (VoucherException voucherException) {
            model.addAttribute("exception", new WebExceptionDto(voucherException));
            return "exception/exception";
        }
        return "voucher/vouchers";
    }
}
