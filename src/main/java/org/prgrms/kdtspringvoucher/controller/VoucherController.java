package org.prgrms.kdtspringvoucher.controller;


import org.prgrms.kdtspringvoucher.dto.VoucherDto;
import org.prgrms.kdtspringvoucher.entity.voucher.Voucher;
import org.prgrms.kdtspringvoucher.service.voucher.VoucherService;
import org.prgrms.kdtspringvoucher.entity.voucher.VoucherTypeNum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String vouchersPage(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher-list";
    }


    @GetMapping("/detail-voucher/{id}")
    public String detailVoucherPage(@PathVariable("id") String id, Model model) {
        Optional<Voucher> voucher = voucherService.getVoucher(UUID.fromString(id));
        model.addAttribute("voucher", voucher.get());
        return "voucher/detail-voucher";
    }

    @GetMapping("/new-voucher")
    public String newVoucherPage() {
        return "voucher/new-voucher";
    }

    @PostMapping("/voucher")
    public String newVoucher(VoucherDto insertVoucherDto) {
        var voucher = VoucherTypeNum.getVoucherType(insertVoucherDto.voucherType(), UUID.randomUUID(), insertVoucherDto.amount());
        voucherService.createVoucher(voucher);
        return "redirect:/vouchers";
    }

    @GetMapping("/update-voucher/{id}")
    public String updateVoucherPage(@PathVariable("id") String id, Model model) {
        Optional<Voucher> voucher = voucherService.getVoucher(UUID.fromString(id));
        model.addAttribute("voucher", voucher.get());
        return "voucher/update-voucher";
    }

    @PostMapping("/update-voucher")
    public String updateVoucher(VoucherDto insertVoucherDto) {
        var voucher = VoucherTypeNum.getVoucherType(insertVoucherDto.voucherType(), insertVoucherDto.voucherId(), insertVoucherDto.amount());
        voucherService.modifyVoucher(voucher);
        return "redirect:/detail-voucher/" + insertVoucherDto.voucherId();
    }


    @GetMapping("/delete-voucher/{id}")
    public String deleteVoucher(@PathVariable("id") String voucherId) {
        voucherService.removeVoucher(UUID.fromString(voucherId));
        return "redirect:/vouchers";
    }
}
