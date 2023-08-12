package com.tangerine.voucher_system.application.voucher.controller;

import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.mapper.VoucherControllerMapper;
import com.tangerine.voucher_system.application.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("/api/v2/vouchers")
public class VoucherViewController {

    private final VoucherService voucherService;
    private final VoucherControllerMapper mapper;

    public VoucherViewController(VoucherService voucherService, VoucherControllerMapper mapper) {
        this.voucherService = voucherService;
        this.mapper = mapper;
    }

    @GetMapping("/form")
    public String voucherFormPage() {
        return "voucher/voucher-form";
    }

    @PostMapping("/create")
    public String createVoucher(CreateVoucherRequest request) {
        voucherService.createVoucher(mapper.requestToParam(request));
        return "redirect:/vouchers";
    }

    @PostMapping("/update")
    public String updateVoucher(UpdateVoucherRequest request) {
        voucherService.updateVoucher(mapper.requestToParam(request));
        return "redirect:/vouchers";
    }

    @GetMapping
    public String voucherList(Model model) {
        model.addAttribute(
                "vouchers",
                mapper.resultsToResponses(voucherService.findVouchers()));
        return "voucher/vouchers";
    }

    @GetMapping("/id/{voucherId}")
    public String voucherById(@PathVariable("voucherId") UUID voucherId, Model model) {
        model.addAttribute(
                "vouchers",
                mapper.resultToResponse(voucherService.findVoucherById(voucherId)));
        return "voucher/voucher-detail";
    }

    @GetMapping("/created/{createdAt}")
    public String voucherByCreatedAt(@PathVariable("createdAt") LocalDate createdAt, Model model) {
        model.addAttribute(
                "vouchers",
                mapper.resultsToResponses(voucherService.findVoucherByCreatedAt(createdAt)));
        return "voucher/vouchers";
    }

    @PostMapping("/delete/{voucherId}")
    public String deleteVoucherById(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return "redirect:/vouchers";
    }
}
