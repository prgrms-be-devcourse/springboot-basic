package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.constant.BaseResponse;
import com.programmers.voucher.domain.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.dto.VoucherResponse;
import com.programmers.voucher.domain.voucher.entity.VoucherType;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vouchers")
public class VoucherApiController {
    private final VoucherService voucherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<VoucherResponse> createVoucher(@Validated @RequestBody VoucherCreateRequest request) {
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

    @GetMapping(params = "type")
    public BaseResponse<List<VoucherResponse>> getVouchersByType(@RequestParam String type) {
        List<VoucherResponse> vouchers = voucherService.getVouchersByType(VoucherType.valueOf(type));
        return BaseResponse.ok(vouchers);
    }

    @PostMapping("/{voucherId}")
    public BaseResponse<Object> deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return BaseResponse.ok();
    }
}
