package org.prgrms.kdt.web.controller;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.web.dto.RequestCreateVoucherDto;
import org.prgrms.kdt.web.dto.RequestUpdateVoucherDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@RequestMapping("/vouchers")
@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String viewVouchers(Model model) {
        List<Voucher> vouchers = voucherService.vouchers();

        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String viewVoucherDetail(@PathVariable("voucherId") UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher-detail";
    }

    @GetMapping("/{voucherId}/edit")
    public String viewVoucherUpdate(@PathVariable("voucherId") UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher-update";
    }

    @PostMapping("/{voucherId}/edit")
    public String voucherUpdate(@PathVariable("voucherId") UUID voucherId,
                                @ModelAttribute RequestUpdateVoucherDto dto,
                                Model model) {
        System.out.println("VoucherController.voucherUpdate");
        Voucher voucher = voucherService.update(voucherId, dto.getVoucherValue());
        model.addAttribute("voucher", voucher);
        return "redirect:/vouchers/{voucherId}";
    }

    @GetMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);
        return "redirect:/vouchers";
    }

    @GetMapping("/new")
    public String viewNewVoucher() {
        return "voucher/voucher-new";
    }

    @PostMapping("/new")
    public String newVoucher(@ModelAttribute RequestCreateVoucherDto dto, Model model, RedirectAttributes redirectAttributes) {
        Voucher voucher = createVoucher(dto);
        model.addAttribute("voucher", voucher);
        redirectAttributes.addAttribute("voucherId", voucher.getVoucherId());
        return "redirect:/vouchers/{voucherId}";
    }

    private Voucher createVoucher(RequestCreateVoucherDto dto) {
        VoucherType voucherType = VoucherType.valueOf(dto.getVoucherType());
        Long voucherValue = dto.getVoucherValue();
        Voucher voucher = voucherService.insert(voucherType, voucherValue);
        return voucher;
    }
}
