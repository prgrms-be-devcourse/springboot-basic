package com.pgms.part1.domain.voucher.controller;

import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.dto.VoucherWebCreateRequestDto;
import com.pgms.part1.domain.voucher.service.VoucherService;
import com.pgms.part1.domain.wallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/vouchers/create")
    public String createVoucher(){
        return "vouchers/vouchersCreatePage";
    }

    @PostMapping("/vouchers/create")
    public String createVoucher(@Valid @ModelAttribute VoucherWebCreateRequestDto voucherWebCreateRequestDto){
        voucherService.createVoucher(voucherWebCreateRequestDto);
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/{id}/delete")
    public String deleteVoucher(@PathVariable Long id){
        voucherService.deleteVoucher(id);
        return  "redirect:/vouchers";
    }
}
