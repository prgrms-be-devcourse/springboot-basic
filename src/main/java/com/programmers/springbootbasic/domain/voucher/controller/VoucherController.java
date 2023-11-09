package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.common.response.CommonResult;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherServiceRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    public CommonResult<String> createVoucher(String voucherType, String value) {
        try {
            voucherService.createVoucher(VoucherServiceRequestDto.builder()
                    .voucherType(Integer.parseInt(voucherType))
                    .value(Long.parseLong(value))
                    .build());
        } catch (NumberFormatException e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(ErrorMsg.NUMBER_FORMAT_MISMATCH.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }
        return CommonResult.getSuccessResult();
    }

    public CommonResult<String> findVoucherById(String voucherId) {
        try {
            Voucher voucher = voucherService.findVoucherById(VoucherServiceRequestDto.builder()
                    .voucherId(UUID.fromString(voucherId))
                    .build());
            return CommonResult.getSingleResult(voucher.getInformation());
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(ErrorMsg.UUID_FORMAT_MISMATCH.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }
    }

    public CommonResult<String> updateVoucher(String voucherId, String value) {
        try {
            voucherService.updateVoucher(VoucherServiceRequestDto.builder()
                    .voucherId(UUID.fromString(voucherId))
                    .value(Long.parseLong(value))
                    .build());
        } catch (NumberFormatException e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(ErrorMsg.NUMBER_FORMAT_MISMATCH.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(ErrorMsg.UUID_FORMAT_MISMATCH.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }
        return CommonResult.getSuccessResult();
    }

    public CommonResult<String> deleteVoucher(String voucherId) {
        try {
            voucherService.deleteVoucher(VoucherServiceRequestDto.builder()
                    .voucherId(UUID.fromString(voucherId))
                    .build());
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(ErrorMsg.UUID_FORMAT_MISMATCH.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return CommonResult.getFailResult(e.getMessage());
        }
        return CommonResult.getSuccessResult();
    }

    public CommonResult<List<String>> findAllVouchers() {
        return CommonResult.getListResult(
                voucherService.findAllVouchers()
                        .stream()
                        .map(Voucher::getInformation)
                        .toList()
        );
    }

    public CommonResult<String> deleteAllVouchers() {
        voucherService.deleteAllVouchers();
        return CommonResult.getSuccessResult();
    }

}
