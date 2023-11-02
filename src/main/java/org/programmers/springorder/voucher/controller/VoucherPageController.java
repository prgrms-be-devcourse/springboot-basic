package org.programmers.springorder.voucher.controller;

import org.programmers.springorder.voucher.dto.VoucherResponseDto;
import org.programmers.springorder.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VoucherPageController {

    private final VoucherService voucherService;

    public VoucherPageController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String getVoucherList(Model model) {
        List<VoucherResponseDto> allVoucher = voucherService.getAllVoucher();
        model.addAttribute("voucherList", allVoucher);
        return "vouchers";
    }

    @GetMapping("/new-voucher")
    public String getNewVoucherPage(){
        return "new-voucher";
    }
}

