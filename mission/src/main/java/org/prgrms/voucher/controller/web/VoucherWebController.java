package org.prgrms.voucher.controller.web;


import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.prgrms.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherWebController {

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {

        this.voucherService = voucherService;
    }

    @PostMapping
    public void createVoucher(@RequestBody @Valid VoucherDto.VoucherRequest request) {

        voucherService.create(request);
    }

    @GetMapping
    public List<VoucherDto.VoucherResponse> findVouchers(@RequestParam @Nullable String voucherType,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate after,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate before
    ) {
        return filterVouchers(voucherType, before, after);
    }

    @GetMapping("/{voucherId}")
    public VoucherDto.VoucherResponse findVoucherById(@PathVariable("voucherId") Long voucherId) {

        Voucher voucher = voucherService.getVoucherById(voucherId);

        return VoucherDto.VoucherResponse.from(voucher);
    }

    @DeleteMapping("/{voucherId}")
    public void deleteVoucherById(@PathVariable Long voucherId) {

        voucherService.deleteVoucherById(voucherId);
    }

    private List<VoucherDto.VoucherResponse> filterVouchers(String voucherType, LocalDate before, LocalDate after) {

        if (voucherType != null && after != null && before != null) {
            return voucherService.list().stream()
                    .filter(voucher -> VoucherType.findByUserInput(voucherType).equals(voucher.getVoucherType()))
                    .filter(voucher -> voucher.getCreatedAt().toLocalDate().isAfter(after))
                    .filter(voucher -> voucher.getUpdatedAt().toLocalDate().isBefore(before))
                    .map(VoucherDto.VoucherResponse::from)
                    .toList();
        }
        if (voucherType != null && after != null) {
            return voucherService.list().stream()
                    .filter(voucher -> VoucherType.findByUserInput(voucherType).equals(voucher.getVoucherType()))
                    .filter(voucher -> voucher.getCreatedAt().toLocalDate().isAfter(after))
                    .map(VoucherDto.VoucherResponse::from)
                    .toList();
        }
        if (voucherType != null && before != null) {
            return voucherService.list().stream()
                    .filter(voucher -> VoucherType.findByUserInput(voucherType).equals(voucher.getVoucherType()))
                    .filter(voucher -> voucher.getUpdatedAt().toLocalDate().isBefore(before))
                    .map(VoucherDto.VoucherResponse::from)
                    .toList();
        }
        if (after != null && before != null) {
            return voucherService.list().stream()
                    .filter(voucher -> voucher.getCreatedAt().toLocalDate().isAfter(after))
                    .filter(voucher -> voucher.getUpdatedAt().toLocalDate().isBefore(before))
                    .map(VoucherDto.VoucherResponse::from)
                    .toList();
        }
        if (voucherType != null) {
            return voucherService.list().stream()
                    .filter(voucher -> VoucherType.findByUserInput(voucherType).equals(voucher.getVoucherType()))
                    .map(VoucherDto.VoucherResponse::from)
                    .toList();
        }
        if (before != null) {
            return voucherService.list().stream()
                    .filter(voucher -> voucher.getUpdatedAt().toLocalDate().isBefore(before))
                    .map(VoucherDto.VoucherResponse::from)
                    .toList();
        }
        if (after != null) {
            return voucherService.list().stream()
                    .filter(voucher -> voucher.getCreatedAt().toLocalDate().isAfter(after))
                    .map(VoucherDto.VoucherResponse::from)
                    .toList();
        }
        return voucherService.list().stream()
                .map(VoucherDto.VoucherResponse::from)
                .toList();
    }
}