package org.prgms.vouchermanagement.voucher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.prgms.vouchermanagement.voucher.domain.dto.VoucherDto;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.service.ThymeleafVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vouchers")
@RequiredArgsConstructor
@Slf4j
public class VoucherController {

    private final ThymeleafVoucherService voucherService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Voucher> allVouchers = voucherService.getVouchers();
        model.addAttribute("allVouchers", allVouchers);
        return "voucher/voucherList";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("voucher", new VoucherDto());
        return "voucher/createNewVoucher";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("voucher")VoucherDto voucherDto) {
        log.info("responseDto type -> {}", voucherDto.getVoucherType());
        log.info("responseDto amount -> {}", voucherDto.getDiscount());
        voucherService.createNewVoucher(voucherDto);
        return "redirect:/vouchers/list";
    }

}
