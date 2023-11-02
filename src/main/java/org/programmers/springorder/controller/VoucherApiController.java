package org.programmers.springorder.controller;

import org.programmers.springorder.dto.voucher.VoucherRequestDto;
import org.programmers.springorder.dto.voucher.VoucherResponseDto;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponseDto>> findAllVouchers() {
        List<VoucherResponseDto> vouchers = voucherService.getAllVoucher();
        return ResponseEntity.ok()
                .body(vouchers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> findVoucher(@PathVariable UUID id) {
        Voucher voucher = voucherService.findById(id);
        return ResponseEntity.ok()
                .body(voucher);
    }

    @PostMapping
    public ResponseEntity<Voucher> addVoucher(@RequestBody VoucherRequestDto request) {
        Voucher savedVoucher = voucherService.createVoucher(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedVoucher);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable UUID id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok()
                .build();
    }
}
