package org.prgms.kdt.application.voucher.controller.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prgms.kdt.application.voucher.controller.dto.VoucherRequestDto;
import org.prgms.kdt.application.voucher.controller.dto.VoucherResponseDto;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("/voucher")
    public String voucherPage(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        List<VoucherResponseDto> voucherResponseDtoList = vouchers.stream()
            .map(voucher -> new VoucherResponseDto(voucher))
            .collect(Collectors.toList());
        model.addAttribute("vouchers", voucherResponseDtoList);
        return "voucher/voucher-list";
    }

    @GetMapping("/voucher/create")
    public String newVoucherPage() {
        return "voucher/voucher-create";
    }

    @PostMapping("/voucher")
    public String newVoucher(VoucherRequestDto voucherRequestDto) {
        voucherService.createVoucher(voucherRequestDto.getVoucher());
        return "redirect:/voucher";
    }

    @GetMapping("/voucher/{voucherId}")
    public String getVoucherDetailPage(Model model, @PathVariable UUID voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucherByVoucherId(voucherId);
        model.addAttribute("voucher", new VoucherResponseDto(voucher.get()));
        return "voucher/voucher-detail";
    }

    @DeleteMapping("/voucher/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteByVoucherId(voucherId);
        return "redirect:/vouchers";
    }
}
