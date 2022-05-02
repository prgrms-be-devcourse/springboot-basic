package com.waterfogsw.voucher.voucher.controller;

import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.dto.PeriodDto;
import com.waterfogsw.voucher.voucher.dto.RequestVoucherDto;
import com.waterfogsw.voucher.voucher.dto.ResponseVoucherDto;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public ResponseVoucherDto voucherAdd(@RequestBody RequestVoucherDto request) {
        final var savedVoucher = voucherService.saveVoucher(request.toDomain());
        return ResponseVoucherDto.of(savedVoucher);
    }

    @GetMapping
    public List<ResponseVoucherDto> voucherList() {
        return voucherService.findAllVoucher()
                .stream()
                .map(ResponseVoucherDto::of)
                .toList();
    }

    @GetMapping("/search")
    public List<ResponseVoucherDto> voucherFindBy(PeriodDto periodDto, VoucherType voucherType) {
        return voucherService.findAllVoucher()
                .stream()
                .filter((v) -> periodDto == null || periodDto.isBetween(v.getCreatedAt()))
                .filter((v) -> voucherType == null || (v.getType() == voucherType))
                .map(ResponseVoucherDto::of)
                .toList();
    }
}
