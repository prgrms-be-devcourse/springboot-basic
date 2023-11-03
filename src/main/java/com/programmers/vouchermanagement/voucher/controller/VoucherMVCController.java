package com.programmers.vouchermanagement.voucher.controller;

import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import com.programmers.vouchermanagement.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Profile("mvc")
@RequestMapping("/vouchers")
@Controller
public class VoucherMVCController {
    private final VoucherService voucherService;

    public VoucherMVCController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/new")
    public String create(CreateVoucherRequest createVoucherRequest) {
        voucherService.create(createVoucherRequest);
        return "redirect:/vouchers";
    }

    @GetMapping("/new")
    public String ViewCreatePage(Model model) {
        return "voucher/voucher-new";
    }

    @GetMapping
    public String viewVouchersPage(Model model) {
        List<VoucherDto> vouchers = voucherService.readAll();
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String readById(@PathVariable("voucherId") UUID voucherId, Model model) {
        try {
            VoucherDto voucher = voucherService.readById(voucherId);
            model.addAttribute("voucher", voucher);
            return "voucher/voucher-detail";
        } catch (RuntimeException e) {
            return "views/404";
        }
    }

    @DeleteMapping("/{voucherId}")
    public String delete(@PathVariable("voucherId") UUID voucherId) {
        voucherService.delete(voucherId);
        return "redirect:/vouchers";
    }

    @DeleteMapping
    public String deleteAll() {
        voucherService.deleteAll();
        return "redirect:/vouchers";
    }

    @PutMapping("/update/{voucherId}")
    public String update(@PathVariable("voucherId") UUID voucherId, CreateVoucherRequest createVoucherRequest) {
        voucherService.update(voucherId, createVoucherRequest);
        return "redirect:/vouchers";
    }
    //TODO: check id

    @GetMapping("/update/{voucherId}")
    public String viewUpdatePage(@PathVariable("voucherId") UUID voucherId, Model model) {
        try {
            VoucherDto voucher = voucherService.readById(voucherId);
            model.addAttribute("voucher", voucher);
            return "voucher/voucher-update";
        } catch (RuntimeException e) {
            return "views/404";
        }
    }
}
