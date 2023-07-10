package org.prgms.vouchermanagement.voucher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.vouchermanagement.voucher.domain.dto.VoucherDto;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.service.ThymeleafVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/detail/{id}")
    public String voucherDetail(@PathVariable("id")UUID voucherId, Model model) {
        Optional<VoucherDto> voucherDto = voucherService.findVoucherDtoById(voucherId);
        if (voucherDto.isEmpty()) {
            return "error";
        }
        model.addAttribute("voucher", voucherDto.get());
        return "voucher/detail";
    }

    @PostMapping("/update")
    public String updateVoucher(@ModelAttribute("voucher") VoucherDto voucherDto) {
        log.info("{} -> ", voucherDto.getVoucherId());
        voucherService.updateVoucher(voucherDto);
        return "redirect:/vouchers/list";
    }
}
