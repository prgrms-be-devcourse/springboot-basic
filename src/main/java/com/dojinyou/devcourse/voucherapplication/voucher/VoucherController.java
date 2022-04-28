package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.Response;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherList;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherResponseList;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequest;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponse;
import org.springframework.stereotype.Controller;

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

    public Response<VoucherResponseList> findAll() {
        VoucherList responsedVoucherList = voucherService.findAll();
        VoucherResponseList voucherResponseList = responsedVoucherList.toResponseList();
        return new Response<>(Response.State.SUCCESS, voucherResponseList);
    }
}
