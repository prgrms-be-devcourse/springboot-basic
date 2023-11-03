package com.prgrms.voucher_manage.domain.voucher.controller;

import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherViewController {
    private final VoucherService voucherService;

    @GetMapping("vouchers")
    public String getVouchers(Model model){
        List<Voucher> vouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", vouchers);
        for (Voucher voucher:
             vouchers) {
            System.out.println("voucher.getId() = " + voucher.getId());
        }
        return "voucher/getVoucherList";
    }

    @GetMapping("vouchers/{voucherId}")
    public String getVoucher(@PathVariable UUID voucherId, Model model){
        Voucher voucher = voucherService.findVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/getVoucher";
    }


    @GetMapping("vouchers/delete/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId){
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }
}
