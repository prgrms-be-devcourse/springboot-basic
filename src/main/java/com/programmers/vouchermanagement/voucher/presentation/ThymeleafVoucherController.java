package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import com.programmers.vouchermanagement.voucher.dto.response.VoucherResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("v2/vouchers")
@RequiredArgsConstructor
public class ThymeleafVoucherController {

    private final VoucherService voucherService;

    @GetMapping
    public String getVouchers(Model model) {
        List<VoucherResponse> vouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @GetMapping("/add")
    public String addForm() {
        return "voucher/addForm";
    }

    @PostMapping("/add")
    public String createVoucher(@Valid VoucherCreationRequest request, RedirectAttributes redirectAttributes) {
        Voucher voucher = voucherService.createVoucher(request);
        redirectAttributes.addAttribute("voucherId", voucher.getId());
        redirectAttributes.addAttribute("status",  true);
        return "redirect:/v2/vouchers/{voucherId}";
    }

    @GetMapping("/{voucherId}")
    public String getVoucher(@PathVariable UUID voucherId, Model model) {
        VoucherResponse voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher";
    }

    @GetMapping("/{voucherId}/edit")
    public String editForm(@PathVariable UUID voucherId, Model model) {
        VoucherResponse voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/editForm";
    }

    @PostMapping("/{voucherId}/edit")
    public String updateVoucher(@ModelAttribute("voucher") @Valid VoucherUpdateRequest request) {
        voucherService.updateVoucher(request);
        return "redirect:/v2/vouchers/{voucherId}";
    }

    @GetMapping("/{voucherId}/delete")
    public String updateVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/v2/vouchers";
    }
}
