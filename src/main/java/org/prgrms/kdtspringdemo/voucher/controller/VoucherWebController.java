package org.prgrms.kdtspringdemo.voucher.controller;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.domain.dto.VoucherRequestDto;
import org.prgrms.kdtspringdemo.voucher.domain.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherWebController {
    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String getAllVouchers(@RequestParam(name = "policy", required = false) String policy, Model model) {
        List<Voucher> vouchers;
        if (policy != null && !policy.isEmpty()) {
            vouchers = voucherService.findByPolicy(policy);
        } else {
            vouchers = voucherService.findAll();
        }

        List<VoucherViewDto> voucherViewDtos = new ArrayList<>();
        vouchers.stream().forEach(voucher -> voucherViewDtos.add(new VoucherViewDto(voucher)));

        model.addAttribute("vouchers", voucherViewDtos);
        return "voucher";
    }

    @GetMapping("/{voucherId}")
    public String viewVoucher(@PathVariable UUID voucherId, Model model) {
        VoucherViewDto voucherViewDto = new VoucherViewDto(voucherService.findById(voucherId));
        model.addAttribute("voucher", voucherViewDto);
        return "voucher_details";
    }

    @PostMapping("/create")
    public String createVoucher(@ModelAttribute VoucherRequestDto voucherRequestDto) {
        VoucherTypeFunction voucherTypeFunction = VoucherTypeFunction.findByCode(voucherRequestDto.getVoucherPolicy());
        voucherService.createVoucher(voucherTypeFunction, voucherRequestDto.getAmount());
        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherId}/edit")
    public String editVoucher(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        VoucherRequestDto voucherRequestDto = new VoucherRequestDto(voucher.getVoucherPolicy().getVoucherType(), voucher.getVoucherPolicy().getAmount());

        model.addAttribute("voucher", voucherRequestDto);
        return "voucher_edit"; // 바우처 정보 수정 페이지로 이동
    }

    @PostMapping("/{voucherId}/edit")
    public String editVoucher(@PathVariable UUID voucherId, @ModelAttribute VoucherRequestDto voucherRequestDto) {
        voucherService.updateVoucher(voucherId, voucherRequestDto);
        return "redirect:/vouchers/" + voucherId;
    }


}
