package org.prgms.voucheradmin.domain.voucher.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.dto.VoucherCreateReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VoucherViewController {
    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @ExceptionHandler(Exception.class)
    public String except(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "views/error";
    }

    @PostMapping("/vouchers/new")
    public String addNewVoucher(VoucherCreateReqDto voucherCreateReqDto) throws IOException{
        voucherService.createVoucher(voucherCreateReqDto);
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) throws IOException {
        List<Voucher> allVouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", allVouchers);
        return "views/voucher/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String viewVouchersPage(@PathVariable UUID voucherId, Model model) throws IOException {
        Voucher voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "views/voucher/voucher";
    }

    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage(Model model)  {
        model.addAttribute("voucherTypes", VoucherType.values());
        return "views/voucher/new-voucher";
    }

    @GetMapping("/vouchers/delete/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId)  {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }
}
