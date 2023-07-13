package com.prgms.springbootbasic.controller;

import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.dto.ModifiedVoucherDto;
import com.prgms.springbootbasic.dto.NewVoucherDto;
import com.prgms.springbootbasic.dto.VoucherDto;
import com.prgms.springbootbasic.persistent.VouchersStorage;
import com.prgms.springbootbasic.service.VoucherService;
import com.prgms.springbootbasic.util.VoucherType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String findVouchers(Model model) {
        try {
            List<VoucherDto> vouchers = voucherService.findAllVouchers();
            model.addAttribute("vouchers", vouchers);
            return "views/vouchers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "views/403";
        }
    }

    @GetMapping("/vouchers/new")
    public String viewFormNewVouchers(Model model) {
        VoucherType[] voucherTypes = VoucherType.values();
        model.addAttribute("voucherTypes", voucherTypes);
        return "views/new-vouchers";
    }

    @PostMapping("/vouchers/new")
    public String createNewVouchers(NewVoucherDto newVoucherDto, Model model) {
        try {
            voucherService.createVoucher(newVoucherDto);
            return "redirect:/vouchers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "views/403";
        }
    }

    @GetMapping("/vouchers/update/{voucherId}")
    public String viewFormUpdateVouchers(@PathVariable("voucherId") UUID voucherId, Model model) {
        model.addAttribute("voucherId", voucherId);
        return "views/update-vouchers";
    }

    @PostMapping("/vouchers/update/{voucherId}")
    public String updateVouchers(@PathVariable("voucherId") UUID voucherId, ModifiedVoucherDto modifiedVoucherDto, Model model) {
        try {
            voucherService.updateVoucher(voucherId, modifiedVoucherDto);
            return "redirect:/vouchers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "views/403";
        }
    }

    @GetMapping("/vouchers/delete/{voucherId}")
    public String deleteVouchers(@PathVariable("voucherId") UUID voucherId, Model model) {
        try {
            voucherService.deleteVoucher(voucherId);
            return "redirect:/vouchers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "views/403";
        }
    }

}
