package com.programmers.voucher.controller;

import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.service.VoucherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
            @RequestParam(required = false) String type) {
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

    @GetMapping({"/pages", "/pages/{pageNum}"})
    public Page<Map<String, Object>> findAllByPage(@PathVariable(required = false) Optional<Integer> pageNum) {
        int page = pageNum.orElseGet(() -> 0);
        Pageable pageable = PageRequest.of(page, 10);
        Page<Map<String, Object>> response = voucherService.findVouchserWithPagination(pageable);
        return response;
    }
}
