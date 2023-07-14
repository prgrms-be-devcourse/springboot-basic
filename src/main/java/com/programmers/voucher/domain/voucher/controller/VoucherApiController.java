package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.constant.BaseResponse;
import com.programmers.voucher.domain.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.dto.VoucherResponse;
import com.programmers.voucher.domain.voucher.entity.VoucherType;
import com.programmers.voucher.domain.voucher.service.VoucherService;
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
    public BaseResponse<VoucherResponse> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherResponse voucher = voucherService.createVoucher(request);
        return BaseResponse.created(voucher);
    }

    @GetMapping
    public BaseResponse<List<VoucherResponse>> getAllVouchers() {
        List<VoucherResponse> vouchers = voucherService.getAllVouchers();
        return BaseResponse.ok(vouchers);
    }

    @GetMapping("/{voucherId}")
    public BaseResponse<VoucherResponse> getVoucher(@PathVariable UUID voucherId) {
        VoucherResponse voucher = voucherService.getVoucher(voucherId);
        return BaseResponse.ok(voucher);
    }

    @GetMapping("type/{voucherType}")
    public BaseResponse<List<VoucherResponse>> getVouchersByType(@PathVariable VoucherType voucherType) {
        List<VoucherResponse> vouchers = voucherService.getVouchersByType(voucherType);
        return BaseResponse.ok(vouchers);
    }

    @PostMapping("/{voucherId}")
    public BaseResponse deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return BaseResponse.ok();
    }
}
