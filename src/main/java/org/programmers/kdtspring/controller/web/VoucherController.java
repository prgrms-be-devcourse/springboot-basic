package org.programmers.kdtspring.controller.web;

import org.programmers.kdtspring.dto.CreateVoucherRequest;
import org.programmers.kdtspring.dto.UpdateVoucherRequest;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String vouchers(Model model) {
        model.addAttribute("vouchers", voucherService.getVouchers());
        return "vouchers";
    }

    @GetMapping("/{voucherId}")
    public String getOneVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        Optional<Voucher> voucher = voucherService.getVoucher(voucherId);

        if (voucher.isEmpty()) {
            return "redirect:/vouchers";
        }

        model.addAttribute("voucher", voucher.get());
        return "voucherDetail";
    }

    @GetMapping("/new")
    public String createVoucherForm(Model model) {
        model.addAttribute("createVoucherRequest", new CreateVoucherRequest());
        return "voucherCreateForm";
    }

    @PostMapping("/new")
    public String createNewVoucher(@ModelAttribute CreateVoucherRequest createVoucherRequest) {

        if (createVoucherRequest.getVoucherType().toString().equalsIgnoreCase("FixedAmountVoucher")) {
            voucherService.createVoucher(createVoucherRequest.getVoucherType().toString(), createVoucherRequest.getAmount());
        }

        if (createVoucherRequest.getVoucherType().toString().equalsIgnoreCase("PercentDiscountVoucher")) {
            voucherService.createVoucher(createVoucherRequest.getVoucherType().toString(), createVoucherRequest.getPercent());
        }
        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherId}/edit")
    public String editVoucherForm(@PathVariable("voucherId") UUID voucherId, Model model) {
        Optional<Voucher> voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher.get());
        return "voucherEdit";
    }

    @PostMapping("/{voucherId}/edit")
    public String editVoucher(@PathVariable("voucherId") UUID voucherId,
                              @ModelAttribute UpdateVoucherRequest updateVoucherRequest) {
        Optional<Voucher> voucher = voucherService.getVoucher(voucherId);
        voucherService.allocateVoucher(voucher.get().getVoucherId(), updateVoucherRequest.getCustomerId());
        return "redirct:/vouchers";
    }

    @PostMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.removeVoucher(voucherId);
        return "redirect:/vouchers";
    }
}