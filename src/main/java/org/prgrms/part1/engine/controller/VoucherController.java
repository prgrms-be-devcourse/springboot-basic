package org.prgrms.part1.engine.controller;

import org.prgrms.part1.engine.controller.dto.VoucherCreateRequestDto;
import org.prgrms.part1.engine.controller.dto.VoucherResponseDto;
import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.service.VoucherService;
import org.prgrms.part1.exception.VoucherException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        List<VoucherResponseDto> allVouchers = voucherService
                .getAllVouchers()
                .stream()
                .map(VoucherResponseDto::new)
                .collect(Collectors.toList());
        model.addAttribute("vouchers", allVouchers);
        return "views/vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage(Model model) {
        return "views/new-voucher";
    }

    @PostMapping("/vouchers/new")
    public String createNewVoucher(VoucherCreateRequestDto voucherCreateRequestDto) {
        Voucher voucher = voucherService.insertVoucher(voucherCreateRequestDto.toEntity());
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String viewVoucherDetailPage(Model model, @PathVariable String voucherId) {
        Voucher voucher = findVoucherByStringId(voucherId);
        model.addAttribute("voucher", new VoucherResponseDto(voucher));
        return "views/voucher";
    }

    @GetMapping("/vouchers/{voucherId}/delete")
    public String deleteVoucher(@PathVariable String voucherId) {
        Voucher voucher = findVoucherByStringId(voucherId);
        voucherService.removeVoucher(voucher);
        return "redirect:/vouchers";
    }

    private Voucher findVoucherByStringId(String voucherId) {
        UUID id;
        try {
            id = UUID.fromString(voucherId);
        }catch (IllegalArgumentException ex) {
            throw new VoucherException("Invalid Id format");
        }
        return voucherService.getVoucher(id);
    }
}
