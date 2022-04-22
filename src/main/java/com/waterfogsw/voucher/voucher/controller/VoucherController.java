package com.waterfogsw.voucher.voucher.controller;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;
import com.waterfogsw.voucher.voucher.dto.VoucherDto;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private VoucherService voucherService;

    public VoucherDto.Response voucherAdd(VoucherDto.Request request) {
        try {
            validateRequest(request);
            Voucher voucher = VoucherDto.Request.toDomain(request);
            Voucher savedVoucher = voucherService.saveVoucher(voucher);
            return VoucherDto.Response.of(savedVoucher, ResponseStatus.OK);
        } catch (IllegalArgumentException e) {
            return VoucherDto.Response.error(ResponseStatus.BAD_REQUEST);
        }
    }

    private void validateRequest(VoucherDto.Request request) {
        if(request.type() == null) throw new IllegalArgumentException();
        if(request.value() == 0) throw new IllegalArgumentException();
    }
}
