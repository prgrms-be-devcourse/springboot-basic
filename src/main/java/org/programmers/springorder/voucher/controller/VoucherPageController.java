package org.programmers.springorder.voucher.controller;

import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherResponseDto;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

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

    @PostMapping("/vouchers")
    public String createVoucher(VoucherRequestDto voucherRequestDto) {
        voucherService.saveNewVoucher(voucherRequestDto);
        return "redirect:vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String getVoucherDetail(@PathVariable UUID voucherId, Model model){
        Voucher voucher = voucherService.getVoucherById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher-detail";
    }

}

