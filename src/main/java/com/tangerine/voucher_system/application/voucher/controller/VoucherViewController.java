package com.tangerine.voucher_system.application.voucher.controller;

import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.controller.mapper.VoucherControllerMapper;
import com.tangerine.voucher_system.application.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherViewController {

    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/form")
    public String voucherFormPage() {
        return "voucher/voucher-form";
    }

    @PostMapping("/create")
    public String createVoucher(CreateVoucherRequest request) {
        voucherService.createVoucher(VoucherControllerMapper.INSTANCE.requestToParam(request));
        return "redirect:/vouchers";
    }

    @PostMapping("/update")
    public String updateVoucher(UpdateVoucherRequest request) {
        voucherService.updateVoucher(VoucherControllerMapper.INSTANCE.requestToParam(request));
        return "redirect:/vouchers";
    }

    @GetMapping("")
    public String voucherList(Model model) {
        List<VoucherResponse> vouchers = voucherService.findVouchers()
                .stream()
                .map(VoucherControllerMapper.INSTANCE::resultToResponse)
                .toList();
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @GetMapping("/id/{voucherId}")
    public String voucherById(@PathVariable("voucherId") UUID voucherId, Model model) {
        VoucherResponse voucher = VoucherControllerMapper.INSTANCE.resultToResponse(voucherService.findVoucherById(voucherId));
        model.addAttribute("vouchers", voucher);
        return "voucher/voucher-detail";
    }

    @GetMapping("/created/{createdAt}")
    public String voucherByCreatedAt(@PathVariable("createdAt") LocalDate createdAt, Model model) {
        List<VoucherResponse> vouchers = voucherService.findVoucherByCreatedAt(createdAt)
                .stream()
                .map(VoucherControllerMapper.INSTANCE::resultToResponse)
                .toList();
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @PostMapping("/delete/{voucherId}")
    public String deleteVoucherById(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return "redirect:/vouchers";
    }
}
