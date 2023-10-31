package com.pgms.part1.domain.voucher.controller;

import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.service.VoucherService;
import com.pgms.part1.domain.wallet.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VoucherWebController {

    private final VoucherService voucherService;
    private final WalletService walletService;

    public VoucherWebController(VoucherService voucherService, WalletService walletService) {
        this.voucherService = voucherService;
        this.walletService = walletService;
    }

    @GetMapping("/vouchers")
    public String getVouchersList(Model model){
        List<VoucherResponseDto> voucherResponseDtos = voucherService.listVoucher();
        model.addAttribute("vouchers", voucherResponseDtos);
        return "vouchers/vouchersListPage";
    }
}
