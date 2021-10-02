package org.prgrms.dev.voucher.controller;

import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.dto.InsertVoucherDto;
import org.prgrms.dev.voucher.domain.dto.UpdateVoucherDto;
import org.prgrms.dev.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String vouchersPage(Model model) {
        List<Voucher> vouchers = voucherService.listVoucher();
        model.addAttribute("vouchers", vouchers);
        return "voucher-list";
    }

    @GetMapping("/detail-voucher/{voucherId}")
    public String detailVoucherPage(@PathVariable("voucherId") String voucherId, Model model) {
        Voucher voucher = voucherService.getVoucher(UUID.fromString(voucherId));
        model.addAttribute("voucher", voucher);
        return "detail-voucher";
    }

    @GetMapping("/new-voucher")
    public String newVoucherPage() {
        return "new-voucher";
    }

    @PostMapping("/voucher")
    public String newVoucher(InsertVoucherDto insertVoucherDto) {
        voucherService.createVoucher(insertVoucherDto);
        return "redirect:/vouchers";
    }

    @GetMapping("/update-voucher/{voucherId}")
    public String updateVoucherPage(@PathVariable("voucherId") String voucherId, Model model) {
        Voucher voucher = voucherService.getVoucher(UUID.fromString(voucherId));
        model.addAttribute("voucher", voucher);
        return "update-voucher";
    }

    @PostMapping("/update-voucher")
    public String updateVoucher(UpdateVoucherDto updateVoucherDto) {
        voucherService.updateVoucherDiscount(updateVoucherDto);
        return "redirect:/detail-voucher/" + updateVoucherDto.getVoucherId();
    }

    @GetMapping("/delete-voucher/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") String voucherId) {
        voucherService.deleteVoucher(UUID.fromString(voucherId));
        return "redirect:/vouchers";
    }
}
