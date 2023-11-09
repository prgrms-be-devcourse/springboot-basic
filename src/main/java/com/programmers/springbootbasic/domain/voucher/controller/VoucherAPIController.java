package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.common.response.CommonResult;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherControllerRequestDto;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherControllerResponseDto;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherServiceRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Profile("default")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vouchers")
public class VoucherAPIController {
    private final VoucherService voucherService;

    @PostMapping
    public CommonResult<String> createVoucher(
            @RequestBody VoucherControllerRequestDto voucherControllerRequestDto) {
        voucherService.createVoucher(VoucherServiceRequestDto.builder()
                .voucherType(voucherControllerRequestDto.getVoucherType())
                .value(voucherControllerRequestDto.getValue())
                .build());
        return CommonResult.getSuccessResult();
    }

    @GetMapping("/{voucherId}")
    public CommonResult<VoucherControllerResponseDto> findVoucherById(
            @PathVariable String voucherId) {
        Voucher voucher = voucherService.findVoucherById(VoucherServiceRequestDto.builder()
                .voucherId(UUID.fromString(voucherId))
                .build());
        return CommonResult.getSingleResult(VoucherControllerResponseDto.of(voucher));
    }

    @PutMapping("/update")
    public CommonResult<String> updateVoucher(
            @RequestBody VoucherControllerRequestDto voucherControllerRequestDto) {
        voucherService.updateVoucher(VoucherServiceRequestDto.builder()
                .voucherId(UUID.fromString(voucherControllerRequestDto.getVoucherId()))
                .value(voucherControllerRequestDto.getValue())
                .build());
        return CommonResult.getSuccessResult();
    }

    @GetMapping
    public CommonResult<List<VoucherControllerResponseDto>> findVouchersBy(
            @RequestParam @Nullable String type, @Nullable @RequestParam String date) {
        List<Voucher> result;
        if (type != null) {
            result = voucherService.findVouchersByType(VoucherServiceRequestDto.builder()
                    .voucherType(Integer.parseInt(type))
                    .build());
        } else if (date != null) {
            result = voucherService.findVouchersByDate(VoucherServiceRequestDto.builder()
                    .date(LocalDate.parse(date))
                    .build());
        } else {
            result = voucherService.findAllVouchers();
        }
        return CommonResult.getListResult(result.stream()
                .map(VoucherControllerResponseDto::of)
                .toList());
    }

    @DeleteMapping("/{voucherId}")
    public CommonResult<String> deleteVoucher(
            @PathVariable String voucherId) {
        voucherService.deleteVoucher(VoucherServiceRequestDto.builder()
                .voucherId(UUID.fromString(voucherId))
                .build());
        return CommonResult.getSuccessResult();
    }

}
