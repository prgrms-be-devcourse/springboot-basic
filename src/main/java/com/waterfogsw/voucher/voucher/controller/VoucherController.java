package com.waterfogsw.voucher.voucher.controller;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.dto.RequestVoucherDto;
import com.waterfogsw.voucher.voucher.dto.Response;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;
import com.waterfogsw.voucher.voucher.dto.ResponseVoucherDto;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response<ResponseVoucherDto> voucherAdd(RequestVoucherDto request) {
        if (request == null) {
            return Response.error(ResponseStatus.BAD_REQUEST);
        }

        try {
            Voucher savedVoucher = voucherService.saveVoucher(request.toDomain());
            return Response.ok(ResponseVoucherDto.of(savedVoucher));
        } catch (IllegalArgumentException e) {
            return Response.error(ResponseStatus.BAD_REQUEST);
        }
    }

    public Response<List<ResponseVoucherDto>> voucherList() {
        final var voucherList = voucherService.findAllVoucher();

        List<ResponseVoucherDto> requestVoucherDtoList = voucherList
                .stream()
                .map(ResponseVoucherDto::of)
                .toList();

        return Response.ok(requestVoucherDtoList);
    }

}
