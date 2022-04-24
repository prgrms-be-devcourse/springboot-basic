package com.waterfogsw.voucher.voucher.controller;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.dto.Response;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;
import com.waterfogsw.voucher.voucher.dto.VoucherDto;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response<VoucherDto> voucherAdd(VoucherDto request) {
        if (request == null) {
            return Response.error(ResponseStatus.BAD_REQUEST);
        }

        try {
            Voucher savedVoucher = voucherService.saveVoucher(request.toDomain());
            return Response.ok(VoucherDto.of(savedVoucher));
        } catch (IllegalArgumentException e) {
            return Response.error(ResponseStatus.BAD_REQUEST);
        }
    }

    public Response<List<VoucherDto>> voucherList() {
        final var voucherList = voucherService.findAllVoucher();

        List<VoucherDto> voucherDtoList = voucherList
                .stream()
                .map(VoucherDto::of)
                .toList();

        return Response.ok(voucherDtoList);
    }

}
