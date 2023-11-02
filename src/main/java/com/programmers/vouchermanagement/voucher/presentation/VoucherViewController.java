package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.dto.VoucherRequestDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher")
public class VoucherViewController {

    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/new")
    public String createVoucherPage() {
        return "voucher/voucher-form";
    }

    @PostMapping
    public String createVoucher(VoucherRequestDto voucherRequestDto) {

        voucherService.createVoucher(voucherRequestDto);

        return "redirect:/voucher";
    }

    @GetMapping
    public String readAllVoucher(Model model) {

        List<VoucherResponseDto> voucherResponseDtos = voucherService.readAllVoucher();
        model.addAttribute("vouchers", voucherResponseDtos);

        return "voucher/voucher-list";
    }

    @GetMapping("/{voucherId}")
    public String readVoucherById(@PathVariable UUID voucherId, Model model) {

        VoucherResponseDto voucherResponseDto = voucherService.readVoucherById(voucherId);
        model.addAttribute("voucher", voucherResponseDto);

        return "voucher/voucher-detail";
    }

    @PostMapping("/delete")
    public String removeAllVoucher() {

        voucherService.removeAllVoucher();

        return "redirect:/voucher";
    }

    @PostMapping("/delete/{voucherId}")
    public String removeVoucherById(@PathVariable UUID voucherId) {

        voucherService.removeVoucherById(voucherId);

        return "redirect:/voucher";
    }
}
