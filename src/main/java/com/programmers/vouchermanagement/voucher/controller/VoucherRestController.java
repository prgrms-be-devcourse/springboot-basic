package com.programmers.vouchermanagement.voucher.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
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
    public VoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        return voucherService.create(createVoucherRequest);
    }

    @GetMapping("/{voucherId}")
    public VoucherResponse findById(@PathVariable UUID voucherId) {
        return voucherService.findById(voucherId);
    }

    @GetMapping
    public List<VoucherResponse> readAllVouchers() {
        return voucherService.readAllVouchers();
    }

    //TODO: 조건별 조회 추가

    @DeleteMapping("/{voucherId}")
    public void deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
    }
}
