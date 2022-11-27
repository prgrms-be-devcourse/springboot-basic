package org.prgrms.voucherapplication.voucher.controller;

import org.prgrms.voucherapplication.voucher.entity.Voucher;
import org.prgrms.voucherapplication.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class VoucherViewController {

    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String viewVoucherPage(Model model) {
        List<Voucher> allVouchers = voucherService.findAll();
        model.addAttribute("vouchers", allVouchers);
        return "/vouchers";
    }

    @PostMapping("/vouchers/new")
    public String viewCreateVoucher(CreateVoucherRequest createVoucherRequest) {
        VoucherType voucherType = createVoucherRequest.getVoucherType();
        Voucher voucher = voucherType.createVoucher(createVoucherRequest.getVoucherId(), createVoucherRequest.getDiscount(), LocalDateTime.now());
        voucherService.create(voucher);
        return "redirect:/vouchers";
    }
}
