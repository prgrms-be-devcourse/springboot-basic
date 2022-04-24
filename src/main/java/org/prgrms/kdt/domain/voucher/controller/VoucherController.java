package org.prgrms.kdt.domain.voucher.controller;

import org.prgrms.kdt.domain.voucher.dto.VoucherCreateRequest;
import org.prgrms.kdt.domain.voucher.dto.VoucherUpdateRequest;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String voucherList(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers/list";
    }

    @GetMapping("/{voucherId}")
    public String voucherDetails(Model model, @PathVariable("voucherId") UUID voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucherById(voucherId);
        voucher.ifPresent(value -> model.addAttribute("voucher", value));
        model.addAttribute("voucherType", VoucherType.values());
        return "vouchers/detail";
    }

    @GetMapping("/new")
    public String voucherCreateShow(Model model) {
        model.addAttribute("voucherType", VoucherType.values());
        return "vouchers/create";
    }

    @PostMapping("/new")
    public String voucherCreate(@Valid VoucherCreateRequest createRequest) {
        voucherService.save(createRequest);
        return "redirect:/vouchers";
    }

    @PutMapping("/{voucherId}")
    public String voucherModify(@Valid VoucherUpdateRequest updateRequest,
                                @PathVariable("voucherId") UUID voucherId) {
        voucherService.update(updateRequest, voucherId);
        return "redirect:/vouchers";
    }

    @DeleteMapping("/{voucherId}")
    public String voucherRemove(@PathVariable("voucherId") UUID voucherId) {
        voucherService.remove(voucherId);
        return "redirect:/vouchers";
    }
}
