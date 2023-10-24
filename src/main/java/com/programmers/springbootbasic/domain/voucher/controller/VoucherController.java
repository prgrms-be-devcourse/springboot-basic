package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.common.response.CommonResult;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    public CommonResult<String> createVoucher(String voucherType, String value) {
        return execute(voucherService::createVoucher, VoucherRequestDto.builder()
                .voucherType(Integer.parseInt(voucherType))
                .value(Long.parseLong(value))
                .build());
    }

    public CommonResult<String> findVoucherById(String voucherId) {
        return execute(voucherService::findVoucherById, VoucherRequestDto.builder()
                .voucherId(UUID.fromString(voucherId))
                .build());
    }

    public CommonResult<String> updateVoucher(String voucherId, String value) {
        return execute(voucherService::updateVoucher, VoucherRequestDto.builder()
                .voucherId(UUID.fromString(voucherId))
                .value(Long.parseLong(value))
                .build());
    }

    public CommonResult<String> deleteVoucher(String voucherId) {
        return execute(voucherService::deleteVoucher, VoucherRequestDto.builder()
                .voucherId(UUID.fromString(voucherId))
                .build());
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

    private CommonResult<String> execute(Consumer<VoucherRequestDto> consumer, VoucherRequestDto voucherRequestDto) {
        try {
            consumer.accept(voucherRequestDto);
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

}
