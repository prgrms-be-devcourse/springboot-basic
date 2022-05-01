package org.prgrms.voucherapp.engine.voucher.controller;

import org.prgrms.voucherapp.engine.voucher.dto.VoucherCreateDto;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
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
    public String vouchersView(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers";
    }

    @GetMapping("/new-voucher")
    public String createVoucherView() {
        return "new-voucher";
    }

    @PostMapping("/vouchers/create")
    public String createVoucher(VoucherCreateDto createDto) {
        voucherService.createVoucher(
                createDto.type(), UUID.randomUUID(), createDto.amount()
        );
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String voucherDetailView(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher-detail";
    }

    @PostMapping("/vouchers/{voucherId}/delete")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.removeVoucher(voucherId);
        return "redirect:/vouchers";
    }

}
