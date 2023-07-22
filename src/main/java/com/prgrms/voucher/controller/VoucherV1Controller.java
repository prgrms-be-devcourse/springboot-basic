package com.prgrms.voucher.controller;

import com.prgrms.common.KeyGenerator;
import com.prgrms.voucher.model.VoucherType;
import java.time.LocalDateTime;
import org.springframework.ui.Model;
import com.prgrms.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/v1/vouchers")
public class VoucherV1Controller {


    private final VoucherService voucherService;
    private final KeyGenerator keyGenerator;

    public VoucherV1Controller(VoucherService voucherService, KeyGenerator keyGenerator) {
        this.voucherService = voucherService;
        this.keyGenerator = keyGenerator;
    }

    @GetMapping("")
    public String getVouchers(
            @RequestParam(value = "voucherType", required = false) VoucherType voucherType
            , @RequestParam(value = "createdAt", required = false) LocalDateTime startCreatedAt
            , Model model) {
        model.addAttribute("vouchers",
                voucherService.getAllVoucherList(voucherType, startCreatedAt));

        return "views/vouchers";
    }

    @GetMapping("/delete/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") int voucherId) {
        voucherService.deleteByVoucherId(voucherId);

        return "redirect:/v1/vouchers";
    }

    @GetMapping("/detail/{voucherId}")
    public String detailVoucher(@PathVariable("voucherId") int voucherId, Model model) {
        model.addAttribute("voucher", voucherService.detailVoucher(voucherId));

        return "views/detail";
    }

    @GetMapping("/new")
    public String viewFormCreateVoucher(Model model) {
        VoucherType[] voucherTypes = VoucherType.values();
        model.addAttribute("voucherTypes", voucherTypes);

        return "views/create";
    }


    @PostMapping("/new")
    public String createVoucher(@RequestParam("voucherType") VoucherType voucherType,
            @RequestParam("discountAmount") double discountAmount) {
        int id = keyGenerator.make();
        voucherService.createVoucher(id, voucherType, discountAmount, LocalDateTime.now());

        return "redirect:/v1/vouchers";
    }

}
