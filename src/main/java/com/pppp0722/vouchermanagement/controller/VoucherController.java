package com.pppp0722.vouchermanagement.controller;

import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoucherController {

    private final VoucherService voucherService;


    public VoucherController(
        VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/find-all")
    public String findAllPage(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "find-all";
    }

    @GetMapping("/find-by-id")
    public String findByIdPage() {
        return "find-by-id";
    }

    @GetMapping("/find-by-created-at")
    public String findByCreatedAtPage() {
        return "find-by-created-at";
    }

    @GetMapping("/find-by-type")
    public String findByTypePage() {
        return "find-by-type";
    }

    @GetMapping("/detail")
    public String detailPage() {
        return "detail";
    }

    @GetMapping("/create")
    public String createPage() {
        return "create";
    }

    @PostMapping("/create")
    public String createPage(CreateVoucherRequest createVoucherRequest) {
        voucherService.createVoucher(UUID.randomUUID(), createVoucherRequest.type(),
            createVoucherRequest.amount(), LocalDateTime.now(), createVoucherRequest.memberId());
        return "redirect:/create";
    }

    @GetMapping("/delete")
    public String deletePage() {
        return "delete";
    }

    @PostMapping("/delete")
    public String deletePage(DeleteVoucherRequest deleteVoucherRequest) {
        voucherService.deleteVoucher(deleteVoucherRequest.voucherId());
        return "redirect:/delete";
    }
}
