package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.response.model.ListResult;
import com.programmers.springbootbasic.common.response.service.ResponseFactory;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    public CommonResult createVoucher(String voucherType, String value) {
        try {
            voucherService.createVoucher(VoucherRequestDto.builder()
                    .voucherType(Integer.parseInt(voucherType))
                    .value(Long.parseLong(value))
                    .build());
        } catch (NumberFormatException e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(ErrorMsg.numberFormatMismatch.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult();
    }

    public CommonResult findVoucherById(String voucherId) {
        try {
            Voucher voucher = voucherService.findVoucherById(VoucherRequestDto.builder()
                    .voucherId(UUID.fromString(voucherId))
                    .build());
            return ResponseFactory.getSuccessResult(voucher.getInformation());
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(ErrorMsg.UUIDFormatMismatch.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
    }

    public CommonResult updateVoucher(String voucherId, String value) {
        try {
            voucherService.updateVoucher(VoucherRequestDto.builder()
                    .voucherId(UUID.fromString(voucherId))
                    .value(Long.parseLong(value))
                    .build());
        } catch (NumberFormatException e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(ErrorMsg.numberFormatMismatch.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(ErrorMsg.UUIDFormatMismatch.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult();
    }

    public CommonResult deleteVoucher(String voucherId) {
        try {
            voucherService.deleteVoucher(VoucherRequestDto.builder()
                    .voucherId(UUID.fromString(voucherId))
                    .build());
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(ErrorMsg.UUIDFormatMismatch.getMessage());
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult();
    }

    public ListResult<String> findAllVouchers() {
        return ResponseFactory.getListResult(
                voucherService.findAllVouchers()
                        .stream()
                        .map(Voucher::getInformation)
                        .toList()
        );
    }

    public CommonResult deleteAllVouchers() {
        voucherService.deleteAllVouchers();
        return ResponseFactory.getSuccessResult();
    }

}
