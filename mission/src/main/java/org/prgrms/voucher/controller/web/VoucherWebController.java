package org.prgrms.voucher.controller.web;


import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public List<VoucherDto.VoucherResponse> findVouchers(@RequestParam("voucherType") Optional<String> voucherType,
                                                         @RequestParam(value = "after") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> after,
                                                         @RequestParam(value = "before") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  Optional<LocalDate> before
    ) {

        List<Voucher> vouchers = voucherService.list();

        if (voucherType.isPresent()) {
            vouchers = vouchers.stream().filter(v -> v.getVoucherType().toString().equals(voucherType.get())).toList();
        }
        if (after.isPresent()) {
            vouchers = vouchers.stream().filter(v -> v.getCreatedAt().toLocalDate().isAfter(after.get())).toList();
        }
        if (before.isPresent()) {
            vouchers = vouchers.stream().filter(v -> v.getCreatedAt().toLocalDate().isBefore(before.get())).toList();
        }

        return vouchers.stream()
                .map(VoucherDto.VoucherResponse::from)
                .toList();
    }

    @GetMapping("/{voucherId}")
    public VoucherDto.VoucherResponse findVoucherById(@PathVariable("voucherId") Long voucherId) {

        Voucher voucher = voucherService.getVoucher(voucherId);

        return VoucherDto.VoucherResponse.from(voucher);
    }

    @DeleteMapping("/{voucherId}")
    public void deleteVoucherById(@PathVariable Long voucherId) {

        voucherService.deleteVoucherById(voucherId);
    }
}