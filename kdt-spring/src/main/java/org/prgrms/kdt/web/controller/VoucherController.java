package org.prgrms.kdt.web.controller;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.web.dto.voucher.RequestCreateVoucherDto;
import org.prgrms.kdt.web.dto.voucher.RequestUpdateVoucherDto;
import org.prgrms.kdt.web.dto.voucher.ResponseVoucherDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/vouchers")
@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String viewVouchers(Model model) {
        List<Voucher> vouchers = voucherService.vouchers();
        List<ResponseVoucherDto> dtoList = mapToDtoList(vouchers);
        model.addAttribute("vouchers", dtoList);
        return "voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String viewVoucherDetail(@PathVariable("voucherId") UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        ResponseVoucherDto dto = mapToDto(voucher);
        model.addAttribute("voucher", dto);
        return "voucher/voucher-detail";
    }

    @GetMapping("/{voucherId}/edit")
    public String viewVoucherUpdate(@PathVariable("voucherId") UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher-update";
    }

    @PostMapping("/{voucherId}/edit")
    public String voucherUpdate(@PathVariable("voucherId") UUID voucherId,
                                @ModelAttribute RequestUpdateVoucherDto dto,
                                Model model) {
        Voucher voucher = voucherService.update(voucherId, dto.getVoucherValue());
        ResponseVoucherDto responseDto = mapToDto(voucher);
        model.addAttribute("voucher", responseDto);
        return "redirect:/vouchers/{voucherId}";
    }

    @GetMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);
        return "redirect:/vouchers";
    }

    @GetMapping("/new")
    public String viewNewVoucher() {
        return "voucher/voucher-new";
    }

    @PostMapping("/new")
    public String newVoucher(@ModelAttribute RequestCreateVoucherDto dto, Model model, RedirectAttributes redirectAttributes) {
        Voucher voucher = createVoucher(dto);
        model.addAttribute("voucher", voucher);
        redirectAttributes.addAttribute("voucherId", voucher.getVoucherId());
        return "redirect:/vouchers/{voucherId}";
    }

    private Voucher createVoucher(RequestCreateVoucherDto dto) {
        VoucherType voucherType = VoucherType.valueOf(dto.getVoucherType());
        Long voucherValue = dto.getVoucherValue();
        Voucher voucher = voucherService.insert(voucherType, voucherValue);
        return voucher;
    }

    private List<ResponseVoucherDto> mapToDtoList(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(voucher -> mapToDto(voucher))
                .collect(Collectors.toList());
    }

    private ResponseVoucherDto mapToDto(Voucher voucher) {
        return new ResponseVoucherDto(voucher.getVoucherId(),  voucher.getValue(), voucher.getType(), voucher.getCreatedAt());
    }
}
