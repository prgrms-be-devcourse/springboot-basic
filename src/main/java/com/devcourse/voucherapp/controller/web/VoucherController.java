package com.devcourse.voucherapp.controller.web;

import com.devcourse.voucherapp.entity.voucher.dto.VoucherCreateRequestDto;
import com.devcourse.voucherapp.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String create(VoucherCreateRequestDto request, Model model) {
        voucherService.create(request);

        return "voucher/vouchers";
    }
}
