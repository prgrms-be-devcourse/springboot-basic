package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class VoucherApiController {
    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public ResponseEntity<List<VoucherDto>> findVouchers() {
        List<VoucherDto> vouchers = voucherService.findVouchers();
        return ResponseEntity.ok().body(vouchers);
    }

    @GetMapping("/vouchers/{voucherId}")
    public ResponseEntity<VoucherDto> findVoucher(@PathVariable UUID voucherId) {
        VoucherDto voucher = voucherService.findVoucher(voucherId);
        return ResponseEntity.ok().body(voucher);
    }

    @PostMapping("/vouchers")
    public ResponseEntity<UUID> createVoucher(@RequestBody VoucherCreateRequest request) {
        UUID voucherId = voucherService.createVoucher(request.getVoucherType(), request.getAmount());
        URI location = URI.create("/api/v1/vouchers/" + voucherId);
        return ResponseEntity.created(location).body(voucherId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/vouchers/delete")
    public void deleteVoucher(@RequestBody UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }
}
