package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.Response;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequest;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponse;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VoucherController {
    public static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";
    private VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    public Response<VoucherResponse> create(VoucherRequest voucherRequest) {
        if (voucherRequest == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
        }
        Voucher voucher = VoucherMapper.requestDtoToDomain(voucherRequest);

        Voucher savedVoucher = voucherService.create(voucher);
        VoucherResponse voucherResponse = VoucherMapper.domainToResponseDto(savedVoucher);

        return new Response<>(Response.State.SUCCESS, voucherResponse);
    }

    public Response<List<VoucherResponse>> findAll() {
        List<Voucher> responsedVoucherList = voucherService.findAll();
        List<VoucherResponse> voucherResponseList = responsedVoucherList.stream().map(VoucherMapper::domainToResponseDto).collect(Collectors.toList());
        return new Response<>(Response.State.SUCCESS, voucherResponseList);
    }
}
