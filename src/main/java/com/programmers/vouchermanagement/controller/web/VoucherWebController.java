package com.programmers.vouchermanagement.controller.web;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.voucher.request.CreateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.request.GetVouchersRequestDto;
import com.programmers.vouchermanagement.dto.voucher.request.UpdateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.response.VoucherResponseDto;
import com.programmers.vouchermanagement.service.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherWebController {

    private final VoucherService voucherService;

    @ModelAttribute("types")
    public VoucherType[] types() {
        return VoucherType.values();
    }

    @GetMapping()
    public String getVouchers(@ModelAttribute GetVouchersRequestDto request, Model model) {
        List<VoucherResponseDto> vouchers = voucherService.getVouchers(request);
        model.addAttribute("vouchers", vouchers);
        return "voucher/list";
    }

    @GetMapping("/create")
    public String createVoucher(Model model) {
        model.addAttribute("types", VoucherType.values());
        model.addAttribute("request", new CreateVoucherRequestDto());
        return "voucher/create";
    }

    @PostMapping("/create")
    public String createVoucher(@Valid @ModelAttribute("request") CreateVoucherRequestDto request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "voucher/create";
        }

        voucherService.createVoucher(request);
        return "redirect:/voucher";
    }

    @GetMapping("/{voucherId}/update")
    public String updateVoucher(@PathVariable UUID voucherId, Model model) {
        VoucherResponseDto voucher = voucherService.getVoucher(voucherId);
        UpdateVoucherRequestDto request = new UpdateVoucherRequestDto();
        request.setAmount(voucher.getAmount());

        model.addAttribute("voucher", voucher);
        model.addAttribute("request", request);
        return "voucher/update";
    }

    @PostMapping("/{voucherId}/update")
    public String updateVoucher(@PathVariable UUID voucherId, @Valid @ModelAttribute("request") UpdateVoucherRequestDto request, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            VoucherResponseDto voucher = voucherService.getVoucher(voucherId);
            model.addAttribute("voucher", voucher);
            model.addAttribute("request", request);
            return "voucher/update";
        }

        voucherService.updateVoucher(voucherId, request);
        return "redirect:/voucher";
    }

    @PostMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/voucher";
    }
}
