package org.programmers.springbootbasic.voucher.controller;

import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        var allVouchers = voucherService.getVoucherList();
        model.addAttribute("vouchers", allVouchers);
        return "vouchers";
    }

    @GetMapping("/{voucherId}")
    public String getVoucher(Model model, @PathVariable UUID voucherId) {
        var voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "/voucher-details";
    }

    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage() {
        return "new-vouchers";
    }

    @PutMapping("/vouchers/details/update")
    public String updateVoucher(UpdateVoucherRequest updateVoucherRequest) {
        voucherService.updateVoucher(updateVoucherRequest.voucherId(), updateVoucherRequest.value());
        return "redirect:/vouchers";
    }

    @PostMapping("/vouchers/new")
    public String addNewVoucher(CreateVoucherRequest createVoucherRequest) {
        voucherService.createVoucher(VoucherType.valueOf(createVoucherRequest.voucherType()), UUID.randomUUID(), createVoucherRequest.value(), LocalDateTime.now());
        return "redirect:/vouchers";
    }

    @DeleteMapping("/vouchers/details/delete")
    public String deleteVoucher(DeleteVoucherRequest deleteVoucherRequest) {
        voucherService.deleteVoucher(deleteVoucherRequest.voucherId());
        return "redirect:/vouchers";
    }
}
