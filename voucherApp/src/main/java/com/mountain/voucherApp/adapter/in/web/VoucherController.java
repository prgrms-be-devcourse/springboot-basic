package com.mountain.voucherApp.adapter.in.web;

import com.mountain.voucherApp.application.port.in.VoucherAppUseCase;
import com.mountain.voucherApp.application.port.in.VoucherCreateDto;
import com.mountain.voucherApp.application.service.VoucherService;
import com.mountain.voucherApp.shared.enums.DiscountPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoucherController {

    private static final Logger log = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;
    private final VoucherAppUseCase voucherAppUseCase;

    // 옮기기
    public static final String EXIST_VOUCHER = "이미 존재하는 바우처 입니다.";

    public VoucherController(VoucherService voucherService, VoucherAppUseCase voucherAppUseCase) {
        this.voucherService = voucherService;
        this.voucherAppUseCase = voucherAppUseCase;
    }

    @GetMapping("/new-voucher")
    public String createVoucherPage(Model model) {
        model.addAttribute("policyMap", DiscountPolicy.discountPolicies);
        return "/voucher/new-voucher";
    }

    @GetMapping("/new-voucher-success")
    public String createVoucherSuccess() {
        return "/voucher/new-voucher-success";
    }


    @GetMapping("/voucher-exist")
    public String existVoucher() {
        return "/voucher/voucher-exist";
    }

    @PostMapping("/new-voucher")
    public String createVoucher(VoucherCreateDto voucherCreateDto) {
        log.info("parameters.. {}", voucherCreateDto.toString());
        if (!voucherAppUseCase.create(voucherCreateDto)) {
            return "redirect:voucher-exist";
        }
        return "redirect:new-voucher-success";
    }

    @GetMapping("/vouchers")
    public String vouchers(Model model) {
        model.addAttribute("vouchers", voucherService.findAll());
        return "/voucher/voucher-list";
    }
}
