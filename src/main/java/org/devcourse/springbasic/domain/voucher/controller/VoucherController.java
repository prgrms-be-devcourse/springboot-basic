package org.devcourse.springbasic.domain.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;
import org.devcourse.springbasic.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping()
    public String findByCriteria(
            @ModelAttribute VoucherDto.Request request,
            Model model
    ) {
        List<VoucherDto.Response> vouchers = voucherService.findByCriteria(request);
        model.addAttribute("vouchers", vouchers);
        return "voucher-list";
    }

    @GetMapping("/{voucherId}")
    public String findById(
            @PathVariable UUID voucherId,
            Model model
    ) {
        VoucherDto.Response response = voucherService.findById(voucherId);
        model.addAttribute("voucher", response);
        return "voucher-details";
    }

    @GetMapping("/create")
    public String createVoucherForm() {
        return "voucher-create-form";
    }

    @PostMapping()
    public String createNewVoucher(
            VoucherDto.SaveRequest request
    ) {
        voucherService.save(request);
        return "redirect:/vouchers";
    }

    @DeleteMapping("/{voucherId}")
    public String deleteVoucher(
            @PathVariable UUID voucherId
    ) {
        voucherService.deleteById(voucherId);
        return "redirect:/vouchers";
    }
}
