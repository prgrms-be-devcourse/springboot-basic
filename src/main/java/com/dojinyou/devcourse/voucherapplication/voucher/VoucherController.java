package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.Response;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherList;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequestDto;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {
    public static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";
    private VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    public Response<VoucherResponseDto> create(VoucherRequestDto voucherRequestDto) {
        if (voucherRequestDto == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
        }
        return this.voucherService.create(voucherRequestDto);
    }

    public Response<VoucherList> findAll() {
        return null;
    }
}
