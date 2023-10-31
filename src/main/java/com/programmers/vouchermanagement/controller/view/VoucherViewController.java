package com.programmers.vouchermanagement.controller.view;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.voucher.request.CreateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.request.UpdateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.response.VoucherResponseDto;
import com.programmers.vouchermanagement.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherViewController {

    private final VoucherService voucherService;

    @ModelAttribute("types")
    public VoucherType[] types() {
        return VoucherType.values();
    }

    @GetMapping()
    public String getVouchers(Model model) {
        List<VoucherResponseDto> vouchers = voucherService.getVouchers();
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
    public String createVoucher(@ModelAttribute CreateVoucherRequestDto request) {
        voucherService.createVoucher(request);
        return "redirect:/voucher";
    }

    @GetMapping("/{voucherId}/update")
    public String updateVoucher(@PathVariable UUID voucherId, Model model) {
        VoucherResponseDto voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        model.addAttribute("request", new UpdateVoucherRequestDto());
        return "voucher/update";
    }

    @PostMapping("/{voucherId}/update")
    public String updateVoucher(@PathVariable UUID voucherId, @ModelAttribute UpdateVoucherRequestDto request) {
        voucherService.updateVoucher(voucherId, request);
        return "redirect:/voucher";
    }

    @PostMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/voucher";
    }
}
