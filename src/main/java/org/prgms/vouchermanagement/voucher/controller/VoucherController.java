package org.prgms.vouchermanagement.voucher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.vouchermanagement.voucher.domain.dto.response.VoucherResponseDto;
import org.prgms.vouchermanagement.voucher.domain.dto.request.VoucherSaveRequestDto;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.exception.VoucherException;
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

    @ExceptionHandler(VoucherException.class)
    public String catchException(Exception e) {
        log.error("Voucher Error ", e);
        return "error";
    }

    @GetMapping("/list")
    public String listVouchers(Model model) {
        List<Voucher> allVouchers = voucherService.getVouchers();
        model.addAttribute("allVouchers", allVouchers);
        return "voucher/voucherList";
    }

    @GetMapping("/create")
    public String createVoucher(Model model) {
        return "voucher/createNewVoucher";
    }

    @PostMapping("/create")
    public String createVoucher(@ModelAttribute("voucher")VoucherSaveRequestDto voucherDto) {
        voucherService.createNewVoucher(voucherDto);
        return REDIRECT_TO_LIST;
    }

    @GetMapping("/detail/{id}")
    public String voucherDetail(@PathVariable("id")UUID voucherId, Model model) {
        Optional<VoucherResponseDto> voucherDto = voucherService.findVoucherById(voucherId);
        voucherDto.ifPresent(dto -> model.addAttribute("voucher", dto));
        return "voucher/detail";
    }

    @PostMapping("/update")
    public String updateVoucher(@ModelAttribute("voucher") VoucherResponseDto voucherDto) {
        voucherService.updateVoucher(voucherDto);
        return REDIRECT_TO_LIST;
    }

    @PostMapping("/delete")
    public String deleteVoucherById(@ModelAttribute("voucherId") UUID voucherID) {
        voucherService.deleteById(voucherID);
        return REDIRECT_TO_LIST;
    }
}
