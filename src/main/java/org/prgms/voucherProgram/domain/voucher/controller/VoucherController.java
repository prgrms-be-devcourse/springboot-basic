package org.prgms.voucherProgram.domain.voucher.controller;

import java.util.List;
import java.util.UUID;

import org.prgms.voucherProgram.domain.voucher.dto.VoucherDto;
import org.prgms.voucherProgram.domain.voucher.dto.VoucherRequest;
import org.prgms.voucherProgram.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String viewVouchersPage(Model model) {
        List<VoucherDto> vouchers = voucherService.findAllVoucher().stream()
            .map(VoucherDto::from)
            .toList();

        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @GetMapping("/new")
    public String viewNewVoucher() {
        return "voucher/new-voucher";
    }

    @PostMapping("/new")
    public String createVoucher(VoucherRequest voucherRequest) {
        voucherService.create(voucherRequest);
        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String findVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        VoucherDto voucherDto = VoucherDto.from(voucherService.findVoucher(voucherId));
        model.addAttribute("voucher", voucherDto);
        return "voucher/voucher-details";
    }

    @PostMapping("/update/{voucherId}")
    public String modifyVoucher(@PathVariable("voucherId") UUID voucherId, VoucherRequest voucherRequest) {
        voucherService.modify(voucherId, voucherRequest);
        return "redirect:/vouchers";
    }

    @GetMapping("/delete/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.delete(voucherId);
        return "redirect:/vouchers";
    }
}
