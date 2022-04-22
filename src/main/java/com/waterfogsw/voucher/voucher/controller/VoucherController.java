package com.waterfogsw.voucher.voucher.controller;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;
import com.waterfogsw.voucher.voucher.dto.VoucherDto;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private VoucherService voucherService;

    public VoucherDto.Response voucherSave(VoucherDto.Request request) {
        try {
            Voucher voucher = VoucherDto.Request.toDomain(request);
            Voucher savedVoucher = voucherService.saveVoucher(voucher);
            return VoucherDto.Response.of(savedVoucher, ResponseStatus.OK);
        } catch (IllegalArgumentException e) {
            return VoucherDto.Response.error(ResponseStatus.BAD_REQUEST);
        }
    }
}
