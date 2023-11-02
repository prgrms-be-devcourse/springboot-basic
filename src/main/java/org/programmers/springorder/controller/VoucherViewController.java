package org.programmers.springorder.controller;

import jakarta.annotation.PostConstruct;
import org.programmers.springorder.dto.voucher.VoucherRequestDto;
import org.programmers.springorder.dto.voucher.VoucherResponseDto;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;
import org.programmers.springorder.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherViewController {

    private final Logger log = LoggerFactory.getLogger(VoucherViewController.class);
    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String getVouchers(Model model) {
        List<VoucherResponseDto> vouchers = voucherService.getAllVoucher();
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @GetMapping("/{id}")
    public String getVoucher(@PathVariable UUID id, Model model) {
        Voucher voucher = voucherService.findById(id);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher";
    }

    @GetMapping("/new-voucher")
    public String newVoucher() {
        return "voucher/addForm";
    }

    @PostMapping("/new-voucher")
    public String newVoucher(VoucherRequestDto request) {   // TODO: discountValue Validation 필요
        voucherService.createVoucher(request);
        return "redirect:/vouchers";
    }

    @PostMapping("/{id}")
    public String deleteVoucher(@PathVariable UUID id) {
        voucherService.deleteVoucher(id);
        return "redirect:/vouchers";
    }

    @PostConstruct  // TODO: 삭제하기
    public void init() {
        VoucherRequestDto request1 = new VoucherRequestDto(1000, VoucherType.FIXED);
        VoucherRequestDto request2 = new VoucherRequestDto(10, VoucherType.PERCENT);
        voucherService.createVoucher(request1);
        voucherService.createVoucher(request2);
    }
}
