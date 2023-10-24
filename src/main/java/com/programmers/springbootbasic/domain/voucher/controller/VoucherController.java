package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.response.model.ListResult;
import com.programmers.springbootbasic.common.response.service.ResponseFactory;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

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
            return ResponseFactory.getFailResult("잘못된 숫자 형식입니다.");
        } catch (Exception e) {
            log.warn(e.toString());
            return ResponseFactory.getFailResult(e.getMessage());
        }
        return ResponseFactory.getSuccessResult();
    }

    public ListResult<String> findAllVoucher() {
        return ResponseFactory.getListResult(
                voucherService.findAllVoucher()
                        .stream()
                        .map(Voucher::getInformation)
                        .toList()
        );
    }

}
