package com.programmers.voucher.controller;

import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.service.VoucherService;
import jakarta.annotation.Nullable;
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

    @PostMapping
    public ResponseEntity<VoucherResponseDto> create(@RequestBody VoucherRequestDto requestDto) {
        VoucherResponseDto response = voucherService.create(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponseDto>> findAllByCondition(
            @RequestParam @Nullable String type) {
        if (type != null) {
            List<VoucherResponseDto> response = voucherService.findVouchersByType(type);
            return ResponseEntity.ok(response);
        }
        List<VoucherResponseDto> response = voucherService.findVouchers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponseDto> findById(@PathVariable UUID id) {
        VoucherResponseDto response = voucherService.findVoucherById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        voucherService.deleteVoucherById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
