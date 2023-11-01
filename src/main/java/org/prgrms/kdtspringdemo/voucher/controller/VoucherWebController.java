package org.prgrms.kdtspringdemo.voucher.controller;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.domain.dto.VoucherRequestDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherWebController {
    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String listVouchers(Model model) {
        List<Voucher> voucherList = voucherService.findAll();
        model.addAttribute("voucherList", voucherList);
        return "voucher";
    }

    @GetMapping("/{voucherId}")
    public String viewVoucher(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher_details";
    }

    @GetMapping("/create")
    public String createVoucherForm(Model model) {
        return "create_voucher";
    }

    @PostMapping("/create")
    public String createVoucher(@ModelAttribute VoucherRequestDto voucherRequestDto) {
        VoucherTypeFunction voucherType = VoucherTypeFunction.findByCode(voucherRequestDto.getVoucherPolicy());

        if (voucherType == VoucherTypeFunction.PERCENT_DISCOUNT_POLICY) {
            // If percentDiscount is selected, validate the amount
            voucherService.createVoucher(voucherType, voucherRequestDto.getPercentage());
            if (voucherRequestDto.getAmount() < 1 || voucherRequestDto.getAmount() > 100) {
                // Handle invalid amount (e.g., show an error message)
                return "redirect:/vouchers"; // Redirect back to the create page
            }
        }

        voucherService.createVoucher(voucherType, voucherRequestDto.getAmount());
        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        return "redirect:/vouchers";
    }

}
