package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.dto.VoucherResponse;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.prgrms.vouchermanager.dto.VoucherRequest.*;
import static com.prgrms.vouchermanager.dto.VoucherResponse.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/vouchers")
public class VoucherRestController {
    private final VoucherService service;

    @GetMapping
    public VoucherListResponse vouchers() {
        return new VoucherListResponse(service.findAll());
    }

    @GetMapping("/{voucherId}")
    public VoucherDetailResponse voucher(@PathVariable UUID voucherId) {
        return toDetailVoucher(service.findById(voucherId));
    }

    @PostMapping("/create")
    public VoucherDetailResponse create(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = service.create(voucherCreateRequest);
        return VoucherResponse.toDetailVoucher(voucher);
    }

    @GetMapping("/{voucherId}/delete")
    public VoucherDeleteResponse delete(@PathVariable UUID voucherId) {
        return new VoucherDeleteResponse(service.delete(voucherId));
    }

    @GetMapping("/findByCondition")
    public VoucherListResponse findByCondition(@RequestBody VoucherFindByConditionRequest request) {
        return new VoucherListResponse(service.findByCondition(request));
    }
}
