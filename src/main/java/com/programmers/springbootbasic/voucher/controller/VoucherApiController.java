package com.programmers.springbootbasic.voucher.controller;

import com.programmers.springbootbasic.voucher.domain.VoucherType;
import com.programmers.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import com.programmers.springbootbasic.voucher.dto.VoucherDto;
import com.programmers.springbootbasic.voucher.dto.VouchersResponseDto;
import com.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/voucher")
@RestController
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/list")
    public ResponseEntity<VouchersResponseDto> list() {
        VouchersResponseDto vouchersResponseDto = voucherService.findAll();

        return ResponseEntity.ok(vouchersResponseDto);
    }

    @PostMapping("/create")
    public ResponseEntity<VoucherDto> save(@RequestBody VoucherCreateRequestDto voucherCreateRequestDto) {
        VoucherDto voucherDto = voucherService.save(voucherCreateRequestDto);

        return ResponseEntity.ok(voucherDto);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<VoucherDto> detail(@PathVariable("id") UUID id) {
        VoucherDto voucherDto = voucherService.findById(id);

        return ResponseEntity.ok(voucherDto);
    }

    @GetMapping("/list/search")
    public ResponseEntity<VouchersResponseDto> findByType(@RequestParam VoucherType type) {
        VouchersResponseDto vouchersResponseDto = voucherService.findByType(type);

        return ResponseEntity.ok(vouchersResponseDto);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody VoucherDto voucherDto) {
        voucherService.update(voucherDto);

        return ResponseEntity.ok(voucherDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable("id") UUID id) {
        voucherService.deleteById(id);

        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll() {
        voucherService.deleteAll();

        return ResponseEntity.ok("All vouchers have been deleted.");
    }
}

