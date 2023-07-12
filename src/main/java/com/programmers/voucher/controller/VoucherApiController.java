package com.programmers.voucher.controller;

import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.DiscountType;
import com.programmers.voucher.domain.VoucherMapper;
import com.programmers.voucher.dto.ServiceDto;
import com.programmers.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.service.VoucherService;
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

    @PostMapping()
    public ResponseEntity<VoucherResponseDto> create(@RequestBody VoucherCreateRequest requestDto) {
        ServiceDto serviceDto = VoucherMapper.convertCreateToServiceDto(requestDto);
        VoucherResponseDto response = voucherService.create(serviceDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<VoucherResponseDto>> findAll() {
        List<VoucherResponseDto> response = voucherService.findVouchers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponseDto> findById(@PathVariable UUID id) {
        VoucherResponseDto response = voucherService.findVoucherById(id);
        return ResponseEntity.ok(response);
    }
}
