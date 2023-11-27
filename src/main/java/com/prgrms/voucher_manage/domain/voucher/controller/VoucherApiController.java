package com.prgrms.voucher_manage.domain.voucher.controller;

import com.prgrms.voucher_manage.base.BaseResponse;
import com.prgrms.voucher_manage.domain.voucher.controller.dto.CreateVoucherReq;
import com.prgrms.voucher_manage.domain.voucher.controller.dto.UpdateVoucherReq;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import com.prgrms.voucher_manage.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class VoucherApiController {
    private final VoucherService service;

    @PostMapping("/vouchers")
    public BaseResponse<UUID> createVoucher(@Valid @RequestBody CreateVoucherReq request) {
        Voucher voucher = service.createVoucher(new CreateVoucherReq(request.type(), request.discountAmount()));
        return new BaseResponse<>(voucher.getId());
    }

    @GetMapping("/vouchers")
    public BaseResponse<List<Voucher>> getVouchers() {
        List<Voucher> vouchers;
        try {
            vouchers = service.getVouchers();
        } catch (Exception e) {
            return new BaseResponse<>(e.getMessage());
        }
        return new BaseResponse<>(vouchers);
    }

    @GetMapping("/vouchers/createdAt")
    public BaseResponse<List<Voucher>> getVouchersByCreatedAt(@RequestParam LocalDateTime createdAt) {
        List<Voucher> vouchers;
        try {
            vouchers = service.getVouchersByCreatedAt(createdAt);
        } catch (Exception e) {
            return new BaseResponse<>(e.getMessage());
        }
        return new BaseResponse<>(vouchers);
    }

    @GetMapping("/vouchers/type")
    public BaseResponse<List<Voucher>> getVouchersByType(@RequestParam VoucherType type) {
        List<Voucher> vouchers;
        try {
            vouchers = service.getVouchersByType(type);
        } catch (Exception e) {
            return new BaseResponse<>(e.getMessage());
        }
        return new BaseResponse<>(vouchers);
    }

    @GetMapping("/vouchers/{voucherId}")
    public BaseResponse<Voucher> getVoucherById(@PathVariable UUID voucherId) {
        Voucher voucher;
        try {
            voucher = service.getVoucherById(voucherId);
        } catch (Exception e) {
            return new BaseResponse<>(e.getMessage());
        }
        return new BaseResponse<>(voucher);
    }

    @PostMapping("/vouchers/{voucherId}")
    public BaseResponse<String> updateVoucher(@PathVariable UUID voucherId, @Valid @RequestBody UpdateVoucherReq request) {
        try {
            service.updateVoucher(voucherId, request);
        } catch (Exception e) {
            return new BaseResponse<>(e.getMessage());
        }
        return new BaseResponse<>();
    }

    @DeleteMapping("/vouchers/{voucherId}")
    public BaseResponse<String> deleteVoucher(@PathVariable UUID voucherId) {
        try {
            service.deleteVoucher(voucherId);
        } catch (Exception e) {
            return new BaseResponse<>(e.getMessage());
        }
        return new BaseResponse<>();
    }
}
