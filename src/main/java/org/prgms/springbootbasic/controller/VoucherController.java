package org.prgms.springbootbasic.controller;

import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherCreateDTO;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String voucherList(Model model) {
        model.addAttribute("vouchers", this.voucherService.findAll());
        return "voucher";
    }


    @PostMapping("/vouchers/new")
    public String createVoucher(VoucherCreateDTO voucherCreateDTO) {
        this.voucherService.createVoucher(voucherCreateDTO);
        return "redirect:/voucher";
    }

    @GetMapping("/vouchers/{customerId}")
    public List<Voucher> findVouchers(@PathVariable UUID customerId, Model model) {
        model.addAttribute("vouchers", voucherService.findVouchers(customerId));
        return this.voucherService.findVouchers(customerId);
    }

    @GetMapping("/vouchers/{voucherId}")
    public Optional<Voucher> findOneVoucher(@PathVariable UUID voucherId) {
        return this.voucherService.findVoucher(voucherId);
    }

    @DeleteMapping("/vouchers")
    public UUID deleteVouchers(UUID customerId) {
        return this.voucherService.deleteVouchers(customerId);
    }

    @PostMapping("/vouchers/{customerId}/{voucherId}")
    public UUID allocateOneVoucher(@PathVariable UUID customerId, @PathVariable UUID voucherId) {
        return this.voucherService.allocateVoucher(customerId, voucherId);
    }
}
