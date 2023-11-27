package com.prgrms.voucher_manage.domain.voucher.controller;

import com.prgrms.voucher_manage.domain.voucher.controller.dto.CreateVoucherReq;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherViewController {
    private final VoucherService voucherService;

    @GetMapping("vouchers/new")
    public String createVoucher() {
        return "voucher/createVoucher";
    }

    @PostMapping("vouchers/new")
    public String createVoucher(@ModelAttribute CreateVoucherReq dto) {
        voucherService.createVoucher(dto);
        return "redirect:/vouchers/get";
    }

    @GetMapping("vouchers/get")
    public String getVouchers(Model model) {
        List<Voucher> vouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher/getVoucherList";
    }

    @GetMapping("vouchers/get/{voucherId}")
    public String getVoucher(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.getVoucherById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/getVoucher";
    }

    @GetMapping("vouchers/delete/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers/get";
    }
}
