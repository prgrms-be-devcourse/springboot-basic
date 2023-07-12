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
    private static final String REDIRECT_TO_LIST = "redirect:/vouchers/list";

    @GetMapping("/list")
    public String listVouchers(Model model) {
        List<Voucher> allVouchers = voucherService.getVouchers();
        model.addAttribute("allVouchers", allVouchers);
        return "voucher/voucherList";
    }

    @GetMapping("/create")
    public String createVoucher(Model model) {
        model.addAttribute("voucher", new VoucherDto());
        return "voucher/createNewVoucher";
    }

    @PostMapping("/create")
    public String createVoucher(@ModelAttribute("voucher")VoucherDto voucherDto) {
        log.info("responseDto type -> {}", voucherDto.getVoucherType());
        log.info("responseDto amount -> {}", voucherDto.getDiscount());
        voucherService.createNewVoucher(voucherDto);
        return REDIRECT_TO_LIST;
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
        log.info("UPDATE -> {} ", voucherDto.getVoucherId());
        voucherService.updateVoucher(voucherDto);
        return REDIRECT_TO_LIST;
    }

    @PostMapping("/delete")
    public String deleteVoucherById(@ModelAttribute("voucherId") UUID voucherID) {
        log.info("DELETE ID -> {}", voucherID);
        voucherService.deleteById(voucherID);
        return REDIRECT_TO_LIST;
    }
}
