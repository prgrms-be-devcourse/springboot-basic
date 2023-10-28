package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.domain.voucher.dto.VoucherControllerRequestDto;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherServiceRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public String createVoucher(VoucherControllerRequestDto voucherControllerRequestDto) {
        voucherService.createVoucher(VoucherServiceRequestDto.builder()
                .voucherType(Integer.parseInt(voucherControllerRequestDto.getVoucherType()))
                .value(Long.parseLong(voucherControllerRequestDto.getValue()))
                .build());
        return "redirect:/";
    }

    @GetMapping("/find")
    public String findVoucher(@RequestParam String voucherId, Model model) {
        model.addAttribute("voucher", voucherService.findVoucherById(VoucherServiceRequestDto.builder()
                .voucherId(UUID.fromString(voucherId))
                .build()));
        return "vouchers/find";
    }

    @PutMapping("/update")
    public String updateVoucher(VoucherControllerRequestDto voucherControllerRequestDto) {
        voucherService.updateVoucher(VoucherServiceRequestDto.builder()
                .voucherId(UUID.fromString(voucherControllerRequestDto.getVoucherId()))
                .value(Long.parseLong(voucherControllerRequestDto.getValue()))
                .build());
        return "redirect:/";
    }

    @DeleteMapping("/delete")
    public String deleteVoucher(VoucherControllerRequestDto voucherControllerRequestDto) {
        voucherService.deleteVoucher(VoucherServiceRequestDto.builder()
                .voucherId(UUID.fromString(voucherControllerRequestDto.getVoucherId()))
                .build());
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Voucher> vouchers = voucherService.findAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers/voucherList";
    }
}
