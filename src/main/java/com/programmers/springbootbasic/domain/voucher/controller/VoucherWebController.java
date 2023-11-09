package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.domain.voucher.dto.VoucherControllerRequestDto;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherServiceRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/vouchers")
public class VoucherWebController {

    private final VoucherService voucherService;

    @GetMapping("/createForm")
    public String createVoucherForm() {
        return "vouchers/createForm";
    }

    @GetMapping("/findForm")
    public String findVoucherForm() {
        return "vouchers/findForm";
    }

    @GetMapping("/updateForm")
    public String updateVoucherForm() {
        return "vouchers/updateForm";
    }

    @GetMapping("/deleteForm")
    public String deleteVoucherForm() {
        return "vouchers/deleteForm";
    }

    @PostMapping("/create")
    public String createVoucher(VoucherControllerRequestDto voucherControllerRequestDto, Model model) {
        try {
            voucherService.createVoucher(VoucherServiceRequestDto.builder()
                    .voucherType(voucherControllerRequestDto.getVoucherType())
                    .value(voucherControllerRequestDto.getValue())
                    .build());
            return "redirect:/";
        } catch (Exception e) {
            log.warn(e.toString());
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/find")
    public String findVoucher(@RequestParam String voucherId, Model model) {
        try {
            model.addAttribute("voucher", voucherService.findVoucherById(VoucherServiceRequestDto.builder()
                    .voucherId(UUID.fromString(voucherId))
                    .build()));
            return "vouchers/find";
        } catch (Exception e) {
            log.warn(e.toString());
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        }
    }

    @PutMapping("/update")
    public String updateVoucher(VoucherControllerRequestDto voucherControllerRequestDto, Model model) {
        try {
            voucherService.updateVoucher(VoucherServiceRequestDto.builder()
                    .voucherId(UUID.fromString(voucherControllerRequestDto.getVoucherId()))
                    .value(voucherControllerRequestDto.getValue())
                    .build());
            return "redirect:/";
        } catch (Exception e) {
            log.warn(e.toString());
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/delete")
    public String deleteVoucher(VoucherControllerRequestDto voucherControllerRequestDto, Model model) {
        try {
            voucherService.deleteVoucher(VoucherServiceRequestDto.builder()
                    .voucherId(UUID.fromString(voucherControllerRequestDto.getVoucherId()))
                    .build());
            return "redirect:/";
        } catch (Exception e) {
            log.warn(e.toString());
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Voucher> vouchers = voucherService.findAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers/voucherList";
    }
}
