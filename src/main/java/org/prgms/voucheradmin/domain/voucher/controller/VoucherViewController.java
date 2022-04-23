package org.prgms.voucheradmin.domain.voucher.controller;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherViewController {
    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // 에러 화면
    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) throws IOException {
        List<Voucher> allVouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", allVouchers);
        return "views/voucher/vouchers";
    }

    // 없는 id -> 에러 화면
    // id 입력이 잘못된다면?
    @GetMapping("/vouchers/{voucherId}")
    public String viewVouchersPage(@PathVariable UUID voucherId, Model model) throws IOException {
        Voucher voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "views/voucher/voucher";
    }
}
