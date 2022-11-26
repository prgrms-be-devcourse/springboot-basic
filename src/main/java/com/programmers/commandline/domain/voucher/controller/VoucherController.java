package com.programmers.commandline.domain.voucher.controller;

import com.programmers.commandline.domain.voucher.dto.VoucherInsetRequestDto;
import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    private final VoucherService voucherService;
    Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping()
    public String findAll(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucherIndex";
    }

    @PostMapping()
    public String insert(VoucherInsetRequestDto requestDto) {
        voucherService.insert(requestDto.type(), requestDto.discount());

        return "redirect:/voucher";
    }
}
