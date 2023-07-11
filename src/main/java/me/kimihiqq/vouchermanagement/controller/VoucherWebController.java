package me.kimihiqq.vouchermanagement.controller;

import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucher.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherWebController {

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String getVoucherList(Model model) {
        List<Voucher> vouchers = voucherService.listVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers/list";
    }

    @GetMapping("/{id}")
    public String getVoucherDetail(@PathVariable UUID id, Model model) {
        Optional<Voucher> voucherOptional = voucherService.findVoucherById(id);
        voucherOptional.ifPresent(voucher -> model.addAttribute("voucher", voucher));
        return "vouchers/detail";
    }

    @GetMapping("/new")
    public String getVoucherCreationForm() {
        return "vouchers/new";
    }

    @PostMapping
    public String createVoucher(@ModelAttribute VoucherDto voucherDto) {
        voucherService.createVoucher(voucherDto);
        return "redirect:/vouchers";
    }

    @PostMapping("/{id}/delete")
    public String deleteVoucher(@PathVariable UUID id) {
        voucherService.deleteById(id);
        return "redirect:/vouchers";
    }
}
