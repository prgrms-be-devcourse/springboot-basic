package com.programmers.springbootbasic.voucher.controller;

import com.programmers.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import com.programmers.springbootbasic.voucher.dto.VoucherDto;
import com.programmers.springbootbasic.voucher.dto.VouchersResponseDto;
import com.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("voucher")
@Controller
public class VoucherViewController {

    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        VouchersResponseDto vouchersResponseDto = voucherService.findAll();
        model.addAttribute("vouchers", vouchersResponseDto);

        return "voucher/voucher-list";
    }

    @GetMapping("/create")
    public String create() {
        return "voucher/voucher-form";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute VoucherCreateRequestDto voucherCreateRequestDto) {
        voucherService.save(voucherCreateRequestDto);

        return "redirect:/voucher/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") UUID id, Model model) {
        VoucherDto voucherDto = voucherService.findById(id);
        model.addAttribute("voucher", voucherDto);

        return "voucher/voucher-detail";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute VoucherDto voucherDto) {
        voucherService.update(voucherDto);

        return "redirect:/voucher/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") UUID id) {
        voucherService.deleteById(id);

        return "redirect:/voucher/list";
    }

    @PostMapping("/deleteAll")
    public String deleteAll() {
        voucherService.deleteAll();

        return "redirect:/voucher/list";
    }
}