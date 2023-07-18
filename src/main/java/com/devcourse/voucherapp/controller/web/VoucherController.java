package com.devcourse.voucherapp.controller.web;

import com.devcourse.voucherapp.entity.voucher.dto.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.voucher.dto.VoucherResponseDto;
import com.devcourse.voucherapp.entity.voucher.dto.VoucherUpdateRequestDto;
import com.devcourse.voucherapp.service.VoucherService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vouchers")
@Profile("dev")
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("/create")
    public String getCreateForm() {
        return "voucher/createForm";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute VoucherCreateRequestDto request) {
        voucherService.create(request);

        return "redirect:/vouchers";
    }

    @GetMapping
    public String findAllVouchers(Model model) {
        List<VoucherResponseDto> vouchers = voucherService.findAllVouchers();
        model.addAttribute("vouchers", vouchers);

        return "voucher/vouchers";
    }

    @GetMapping("/{voucherId}/update")
    public String getUpdateForm(@PathVariable String voucherId, Model model) {
        VoucherResponseDto voucher = voucherService.findVoucherById(voucherId);
        model.addAttribute("voucher", voucher);

        return "voucher/updateForm";
    }

    @PostMapping("/{voucherId}/update")
    public String update(@ModelAttribute VoucherUpdateRequestDto request) {
        voucherService.update(request);

        return "redirect:/vouchers";
    }
}
