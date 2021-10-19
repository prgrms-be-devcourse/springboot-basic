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
        List<Voucher> vouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher-list";
    }

    @GetMapping("/detail-voucher/{id}")
    public String detailVoucherPage(@PathVariable("id") String id, Model model) {
        Voucher voucher = voucherService.getVoucher(UUID.fromString(id));
        model.addAttribute("voucher", voucher);
        return "voucher/detail-voucher";
    }

    @GetMapping("/new-voucher")
    public String newVoucherPage() {
        return "voucher/new-voucher";
    }

    @PostMapping("/voucher")
    public String newVoucher(InsertVoucherDto insertVoucherDto) {
        voucherService.addVoucher(insertVoucherDto);
        return "redirect:/vouchers";
    }

    @GetMapping("/update-voucher/{id}")
    public String updateVoucherPage(@PathVariable("id") String id, Model model) {
        Voucher voucher = voucherService.getVoucher(UUID.fromString(id));
        model.addAttribute("voucher", voucher);
        return "voucher/update-voucher";
    }

    @PostMapping("/update-voucher")
    public String updateVoucher(UpdateVoucherDto updateVoucherDto) {
        voucherService.modifyVoucher(updateVoucherDto);
        return "redirect:/detail-voucher/" + updateVoucherDto.getVoucherId();
    }

    @GetMapping("/delete-voucher/{id}")
    public String deleteVoucher(@PathVariable("id") String voucherId) {
        voucherService.removeVoucher(UUID.fromString(voucherId));
        return "redirect:/vouchers";
    }
}
