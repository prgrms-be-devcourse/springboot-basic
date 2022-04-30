package com.waterfogsw.voucher.voucher.controller;

import com.waterfogsw.voucher.voucher.dto.RequestVoucherDto;
import com.waterfogsw.voucher.voucher.dto.ResponseVoucherDto;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/api/v1/vouchers/new")
    public ResponseVoucherDto voucherAdd(@RequestBody RequestVoucherDto request) {
        final var savedVoucher = voucherService.saveVoucher(request.toDomain());
        return ResponseVoucherDto.of(savedVoucher);
    }

    @GetMapping("/api/v1/vouchers")
    public List<ResponseVoucherDto> voucherList() {
        return voucherService.findAllVoucher()
                .stream()
                .map(ResponseVoucherDto::of)
                .toList();
    }
}
