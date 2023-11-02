package com.programmers.vouchermanagement.voucher.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.SearchCreatedAtRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public VoucherResponse create(@Valid CreateVoucherRequest createVoucherRequest) {
        return voucherService.create(createVoucherRequest);
    }

    @GetMapping
    public List<VoucherResponse> readAllVouchers() {
        return voucherService.readAllVouchers();
    }

    @GetMapping("/{voucherId}")
    public VoucherResponse findById(@PathVariable UUID voucherId) {
        return voucherService.findById(voucherId);
    }

    @DeleteMapping("/{voucherId}")
    public void deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
    }

    @GetMapping("/type/{typeName}")
    public List<VoucherResponse> findByType(@PathVariable String typeName) {
        VoucherType voucherType = VoucherType.findVoucherTypeByName(typeName);
        return voucherService.findByType(voucherType);
    }

    @GetMapping("/creation-date")
    public List<VoucherResponse> findByCreatedAt(@Valid SearchCreatedAtRequest request) {
        return voucherService.findByCreatedAt(request);
    }
}
